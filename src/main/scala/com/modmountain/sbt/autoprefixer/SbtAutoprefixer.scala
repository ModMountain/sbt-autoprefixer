package com.modmountain.sbt.autoprefixer

import java.io.File

import sbt._
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.pipeline.Pipeline
import sbt.Keys._
import scala.sys.process.ProcessBuilder

import scala.sys.process.ProcessLogger

object Import {
  val autoprefixer = TaskKey[Pipeline.Stage]("autoprefixer", "Autoprefixes CSS files.")
}

object SbtAutoprefixer extends AutoPlugin {

  override def requires = SbtWeb

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    includeFilter in autoprefixer := "*.css",
    excludeFilter in autoprefixer := HiddenFileFilter,
    target in autoprefixer := webTarget.value / autoprefixer.key.label,
    deduplicators += SbtWeb.selectFileFrom((target in autoprefixer).value),
    autoprefixer := gzipFiles.value
  )

  def gzipFiles: Def.Initialize[Task[Pipeline.Stage]] = Def.task {
    mappings =>
      val targetDir = (target in autoprefixer).value
      val include = (includeFilter in autoprefixer).value
      val exclude = (excludeFilter in autoprefixer).value
      val gzipMappings = for {
        (file, path) <- mappings if !file.isDirectory && include.accept(file) && !exclude.accept(file)
      } yield {
          val outputPath = path
          val outputFile = targetDir / outputPath
          outputFile.getParentFile.mkdirs()
          compile(file, outputFile)
          (outputFile, outputPath)
        }
      mappings ++ gzipMappings
  }

  def compile(cssFile: File, outfile: File) = {
    try {
      runCompiler(
        autoprefixerCommand ++ Seq("-o", outfile.getAbsolutePath, cssFile.getAbsolutePath)
      )

    } catch {
      case e: RuntimeException =>
        throw new Exception("Autoprefixer: " + e.getMessage)
    }
  }

  // Gets the Autoprefixer command based on the operating system
  private def autoprefixerCommand = if (isWindows) Seq("cmd", "/c","postcss.cmd", "--use", "autoprefixer") else Seq("")

  private val isWindows = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0

  // Runs a given command and process the output
  private def runCompiler(command: ProcessBuilder) = {
    val err = new StringBuilder
    val out = new StringBuilder

    val capturer = ProcessLogger(
      (output: String) => out.append(output + "\n"),
      (error: String) => err.append(error + "\n"))

    val process = command.run(capturer)
    if (process.exitValue != 0 || err.nonEmpty) {
      throw new RuntimeException(err.toString)
    }
  }
}