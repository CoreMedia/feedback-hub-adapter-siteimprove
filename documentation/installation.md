# Installation

--------------------------------------------------------------------------------

\[[Up](README.md)\] \[[Top](#top)\]

--------------------------------------------------------------------------------

## Table of Content

1. [Introduction](#introduction)
2. [Use Git](#use-git)
3. [Download Release](#download-release)
4. [Activate the Plugin](#activate-the-plugin)

## Introduction

Depending on your setup and your plans, you can integrate this project in different ways.

* If you want to use the plugin in your project, clone or fork the repository.
* If you do not want to use GitHub, proceed as described in [Download Release](#download-release).
* If you want to contribute a new feature or a bugfix, as an external developer, you need a fork of the repository to create a Pull Request.

## Use Git

Clone this repository or your fork. Make sure to use the suitable branch
for your workspace version (see [README](../README.md)). A fork is required if
you plan to customize the plugin.

Continue with [Activate the plugin](#activate-the-plugin).

## Download Release

Go to [Release](https://github.com/CoreMedia/feedback-hub-adapter-siteimprove/releases) and download the version that matches your CMCC release version.
The ZIP file provides the Maven workspace of the plugin.

## Activate the Plugin

The Siteimprove feedback hub adapter is a plugin for studio-server and studio-client.
The deployment of plugins is described [here](https://documentation.coremedia.com/cmcc-10/artifacts/2101/webhelp/coremedia-en/content/ApplicationPlugins.html).

In short, for a quick development roundtrip:
1. Build your Blueprint.
2. Build the `feedback-hub-adapter-siteimprove` plugin with `mvn clean install`.
   Checkpoint: There should be zip files in the target directories of the `studio-client` and `studio-server` modules now.
3. Create a directory for studio-server plugins, e.g. `/tmp/studio-server-plugins`,
   and copy `feedback-hub-adapter-siteimprove/studio-server/target/studio-server.feedback-hub-adapter-siteimprove-<version>.zip`
   into that directory.
4. Start the studio server as usual, e.g. `mvn spring-boot:run`, with an additional property `-Dplugins.directory=/tmp/studio-server-plugins`
5. Start the studio client with an additional property `-DadditionalPackagesDirs=/.../feedback-hub-adapter-siteimprove/studio-client/target/app`

Now the plugin is running.  You won't yet notice it though, until you configure a connection
and restart the studio server.

## Frontend Extension

The principal of Siteimprove is to crawl a target website and to anaylze its pages. The Siteimprove Feedback Hub Plugin
needs to match contentIds to a crawled page in the Siteimprove index. This is why we need to add a special HTML meta-data tag 
to each CoreMedia delivered content page or article.  The meta tag must follow the following format and needs to show the 
corresponding pages content Id:

`<meta name="coremedia:content-id" content="${self.contentId}" />`
