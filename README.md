# dependency-inspection
Given a bunch of JAR files it searches on the maven central repository all the dependencies for Maven or Gradle

<strong>What it does?</strong>
<br />
<br />
Well is actually quite simple.  You give as input a folder that contains a bunch of JAR files (yes it has a UI) and then it scans that folder.  Let's say that one of the jar files is:
<br />
<br />
<code>spring-webmvc-5.2.9.RELEASE.jar</code>
<br />
<br />
It gets the name and version of the JAR from the name in this case it will be:
<br />
<br />
<code>spring-webmvc</code><br />
<code>5.2.9.RELEASE</code>
<br />
<br />
With that it searches into maven central repository for the dependency.
<br />
<br />
<strong>What if the name is a mess?</strong>
<br />
<br />
Well, if the name is a mess it will try to look for information inside the JAR file in the MANIFEST.MF.  Sometimes the author(s) put information in there regarding name, package, version and so on, so it tries to get the information from there.
<br />
<br />
<strong>What if the name is a mess and the JAR don't have a MANIFEST.MF?</strong>
<br />
<br />
In that case it gets the JAR name (which is the closest thing we have to a name) and then takes each package (yes all of them) from the JAR and tries to search with the given name and each one of the packages as the group id.  Sometimes it will find a match. For instance if the JAR name is (considering we are still taking about the previous example):
<br />
<br />
<code>spring.jar</code>
<br />
<br />
Then it will try to search for the name <strong>spring</strong> with the group id:
<br />
<br />
<code>org</code>
<br />
<code>org.springframework</code>
<br />
<code>org.springframework.web</code>
<br />
<code>... you get the idea</code>
<br />
<br />
If all of this fails then it tells you that it was not found and that's it.
<br />
<br />
<strong>How do I get the results?</strong>
<br />
<br />
If you are using the UI you will get 2 files in the selected folder that has all the JARs on it.  If you are not using the UI there is an object you can extract with the lists of found and missing.
<br />
<br />
Now that you know all of this, if you like the project go ahead and <br /><br />
<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=CSQRVLE2D43NU&item_name=Buy+me+a+dinner+with+my+wife%21&currency_code=USD">
  <strong>Buy me a dinner with my wife!</strong>
</a>
