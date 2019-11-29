![CoreMedia Labs Logo](https://documentation.coremedia.com/badges/banner_coremedia_labs_wide.png "CoreMedia Labs Logo Title Text")


# Siteimprove Integration for the CoreMedia Feedback Hub
The _CoreMedia Feedback Hub_ offers the possibility to provide feedback for CoreMedia content. 
It is possible to connect external systems to Feedback Hub in order to collect feedback. 

This projects integrates the _Siteimprove_ REST API into the Feedback Hub of CoreMedia.
It also enables the Siteimprove widget for the preview CAE.

### Documentation

https://github.com/CoreMedia/feedback-hub-adapter-siteimprove/tree/master/documentation

### Issue Tracker

https://github.com/CoreMedia/feedback-hub-adapter-siteimprove/issues

### Installation
 
- From the project's root folder, clone this repository as submodule into the extensions folder. Make sure to use the branch name that matches your workspace version. 
```
git submodule add  -b 1907.1 https://github.com/CoreMedia/coremedia-studio-hub modules/extensions/feedback-hub-adapter-siteimprove
```

- Use the extension tool in the root folder of the project to link the modules into your workspace.
 ```
mvn -f workspace-configuration/extensions com.coremedia.maven:extensions-maven-plugin:LATEST:sync -Denable=feedback-hub-adapter-siteimprove
```

- Rebuild the workspace

For CI runs:
- Ensure that the matching branch name is set in the _.gitmodules_ file, e.g.:

```
[submodule "modules/extensions/coremedia-studio-hub"]
	path = modules/extensions/feedback-hub-adapter-siteimprove
	url = https://github.com/CoreMedia/feedback-hub-adapter-siteimprove.git
	branch = 1907.2
```

For the IDEA import:
- Ignore folder _.remote-package_
- Disable "Settings > Compiler > Clear output directory on rebuild"
 
