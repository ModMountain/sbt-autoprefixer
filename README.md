# sbt-autoprefixer
[sbt-web](https://github.com/sbt/sbt-web) plugin that uses [Autoprefixer](https://github.com/postcss/autoprefixer) to parse CSS and add vendor prefixes to CSS rules using values from [Can I Use](http://caniuse.com). 

## Prerequisites
- [nodejs](https://nodejs.org/en/)
- [NPM](https://www.npmjs.com/)
- [SBT](http://www.scala-sbt.org/)

## Setup
sbt-autoprefixer is not currently on Maven central or another repository. I plan to resolve this in the next week or two but for the time being, please install sbt-autoprefixer locally. To do this, run the following commands in order:

```
git clone https://github.com/ModMountain/sbt-autoprefixer.git
cd sbt-autoprefixer
sbt clean publishLocal
```

Once you have published sbt-autoprefixer locally, install PostCSS and Autoprefixer:
```
npm install --global postcss autoprefixer
```
If you have trouble running this command, try running the command as a super user.

Once installed, make sure your project's build file has sbt-web enabled:
```
lazy val root = (project.in file(".")).enablePlugins(SbtWeb)
```

Finally, add sbt-autoprefixer to the sbt-web asset pipeline:
```
pipelineStages := Seq(autoprefixer)
```

## Configuration
Currently there are no configuration options. I plan to add some in the future but for now you are stuck with Autoprefixer's defaults.

## Credit
[sbt-autoprefixer](https://github.com/matthewrennie/sbt-autoprefixer): Without this existing, I wouldn't have created my own version

[sbt-gzip](https://github.com/sbt/sbt-gzip): Easy to read code that let me figure out how sbt-web pipeline plugins work
