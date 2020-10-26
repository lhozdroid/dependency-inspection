package dev.lhoz.dependency.inspection.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

import dev.lhoz.dependency.inspection.service.GradleInspectionService;
import dev.lhoz.dependency.inspection.service.MavenInspectionService;
import dev.lhoz.dependency.inspection.service.thread.InspectionThread;

public class MainFrame {

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					final MainFrame window = new MainFrame();
					window.frameInspection.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JTextArea console;
	private JScrollPane consoleScroll;
	private JFrame frameInspection;
	private JButton gradleButton;
	private JButton mavenButton;
	private JPanel panel;
	private JButton searchButton;
	private JTextField searchField;
	private JLabel searchLabel;

	/**
	 * Create the application.
	 */
	public MainFrame() {
		this.initialize();
	}

	/**
	 *
	 */
	public void unlock() {
		this.gradleButton.setEnabled(true);
		this.mavenButton.setEnabled(true);
		this.searchButton.setEnabled(true);
		this.searchField.setEnabled(true);
	}

	/**
	 *
	 */
	private void gradle() {
		this.lock();

		final ExecutorService executor = Executors.newSingleThreadExecutor();

		final String path = this.searchField.getText();
		final GradleInspectionService inspectionService = GradleInspectionService.getInstance();

		final InspectionThread<GradleInspectionService> thread = new InspectionThread<>();
		thread.setPath(path);
		thread.setInspectionService(inspectionService);
		thread.setFrame(this);

		executor.execute(thread);

		executor.shutdown();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frameInspection = new JFrame();
		this.frameInspection.setTitle("Dependency Inspection");
		this.frameInspection.getContentPane().setBackground(Color.WHITE);
		this.frameInspection.getContentPane().setLayout(null);

		this.consoleScroll = new JScrollPane();
		this.consoleScroll.setAutoscrolls(true);
		this.consoleScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.consoleScroll.setBounds(10, 114, 372, 97);
		this.frameInspection.getContentPane().add(this.consoleScroll);

		this.console = new JTextArea();
		this.console.setForeground(Color.GREEN);
		this.console.setBackground(Color.BLACK);
		this.consoleScroll.setViewportView(this.console);
		Logger.getInstance().setConsole(this.console);

		final DefaultCaret caret = (DefaultCaret) this.console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		this.panel = new JPanel();
		this.panel.setBounds(10, 11, 372, 97);
		this.frameInspection.getContentPane().add(this.panel);
		this.panel.setLayout(null);

		this.searchLabel = new JLabel("Folder with JARs");
		this.searchLabel.setBounds(10, 11, 352, 14);
		this.panel.add(this.searchLabel);

		this.searchField = new JTextField();
		this.searchField.setBounds(10, 28, 277, 20);
		this.panel.add(this.searchField);
		this.searchField.setColumns(10);

		this.searchButton = new JButton("Search");
		this.searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MainFrame.this.search();
			}
		});
		this.searchButton.setBounds(297, 27, 65, 23);
		this.panel.add(this.searchButton);

		this.mavenButton = new JButton("Maven");
		this.mavenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MainFrame.this.maven();
			}
		});
		this.mavenButton.setBounds(273, 63, 89, 23);
		this.panel.add(this.mavenButton);

		this.gradleButton = new JButton("Gradle");
		this.gradleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MainFrame.this.gradle();
			}
		});
		this.gradleButton.setBounds(174, 63, 89, 23);
		this.panel.add(this.gradleButton);
		this.frameInspection.setResizable(false);
		this.frameInspection.setBounds(100, 100, 401, 251);
		this.frameInspection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Logger.getInstance().log("Please select a folder to start...");
	}

	/**
	 *
	 */
	private void lock() {
		this.gradleButton.setEnabled(false);
		this.mavenButton.setEnabled(false);
		this.searchButton.setEnabled(false);
		this.searchField.setEnabled(false);
	}

	/**
	 *
	 */
	private void maven() {
		this.lock();

		final ExecutorService executor = Executors.newSingleThreadExecutor();

		final String path = this.searchField.getText();
		final MavenInspectionService inspectionService = MavenInspectionService.getInstance();

		final InspectionThread<MavenInspectionService> thread = new InspectionThread<>();
		thread.setPath(path);
		thread.setInspectionService(inspectionService);
		thread.setFrame(this);

		executor.execute(thread);

		executor.shutdown();
	}

	/**
	 *
	 */
	private void search() {
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		final Integer status = chooser.showOpenDialog(null);

		if (status == JFileChooser.APPROVE_OPTION) {
			final File folder = chooser.getSelectedFile();
			if (folder == null || !folder.isDirectory()) {
				Logger.getInstance().log("The selection must a folder...");
			} else {
				this.searchField.setText(folder.getAbsolutePath());
			}
		}
	}
}
