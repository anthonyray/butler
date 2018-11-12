# Butler
A toy static website generator from scratch, written in Clojure. 

## Introduction

`butler` is a CLI that helps me generate pages for my personal [website](https://anthonyray.github.io/). 

The goal of `butler` is to generate web pages from markdown source files.

It's also a toy project that I'll use to learn Clojure !

## Installation

Butler can be built and used on your machine if you have leiningen installed. 

### In brief 

Run the following commands :
```
./install.sh
./deploy.sh
```

And you're good to go ! 
### Details
What happens behind the scenes : 
The install script runs the following command : 
```
lein uberjar
```
This will generate a standalone jar for butler. 


The `deploy` script will then create an executable from the standalone jar. 
```
cat stub.sh target/uberjar/butler-0.1.0-SNAPSHOT-standalone.jar > butler && chmod +x butler
```
This will generate an executable that you can add to your PATH. 

I decided to create a deploy script that will copy this executable to `~/Applications/butler/` so that it's in my path. 


## API

    $ butler 
    Butler is a tool to generate pages for my personal website.
    
    Several tasks are available:
    build             Reads the article sources and renders them to HTML
    status            Retrieve latest updates for the website.
    version           Returns the version of butler
    deploy            Deploys generated HTML file to Github pages

### Subcommands

- `version`
- `build`
- `deploy`
- `status`

**version (paused)**

    $ butler version
    0.1.0

**build**

    $ butler build
     *  Reading the content of ./articles
     *  Found 2 entrie(s) : 
     *      - example1.md
     *      - example2.md
     *  Generated 2 html pages using template : ./template/basic.tmpl.html
     *  Using stylesheet : ./styles/main.css
     *  Ignored 10 entrie(s)
     *  🎩 Done !

**deploy**

    $ butler deploy
     * Checking for newly generated files ... 
     * Adding files to index ...
     * Commiting files ... 
     * Pushing to Github. 
     * 🎩 Done !

**status**

    $ butler status
     * Last pushed was 10 Oct. 2018, at 09:00am. 
     * Commit sha : b123ef56
     * Up on https://anthonyray.github.io   

## Features

Butler has the following functionality : 

- [ ]  parses Github markdown
- [ ]  uses a templating system to generate html pages
- [ ]  Reads a configuration file

## Usage
### Generating articles

The root has to follow this structure :
```
- articles
|--- article1.md
|--- article2.md

- templates
|--- article.html
```



