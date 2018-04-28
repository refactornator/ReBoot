package application.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class NpmRunner implements CommandLineRunner {

  private Environment environment;
  private static Logger log = LoggerFactory.getLogger(NpmRunner.class);

  @Autowired
  public NpmRunner(Environment environment) {
    this.environment = environment;
  }

  @Override
  public void run(String... args) throws Exception {
    if (!environment.acceptsProfiles("production") && !environment.acceptsProfiles("test")) {
      AtomicBoolean registered = (AtomicBoolean) Restarter.getInstance().getOrAddAttribute("npmStarted", AtomicBoolean::new);
      boolean alreadyRun = registered.getAndSet(true);
      if (!alreadyRun) {
        startFrontend();
      }
    }
  }

  private void npmStart(File frontendDir) throws IOException {
    ProcessExecutor process = command("npm", "start")
      .directory(frontendDir)
      .redirectOutput(Slf4jStream.of(LoggerFactory.getLogger("npm")).asInfo())
      .redirectError(Slf4jStream.of(LoggerFactory.getLogger("npm")).asError());

    process.start();
  }

  private void startFrontend() throws IOException, TimeoutException, InterruptedException {
    File frontendDir = locate("./frontend/package.json", "../frontend/package.json");
    log.info("Your frontend dependencies seem to be out of date. Running yarn install. Hang tight...");
    npmInstall(frontendDir);
    npmStart(frontendDir);
  }

  private boolean yarnCheck(File frontendDir) throws InterruptedException, TimeoutException, IOException {
    ProcessExecutor process = command("npm", "check")
      .directory(frontendDir)
      .exitValueAny();

    return process.execute().getExitValue() == 0;
  }

  private void npmInstall(File frontendDir) throws InterruptedException, TimeoutException, IOException {
    command("npm", "install")
      .directory(frontendDir)
      .redirectOutput(Slf4jStream.of(LoggerFactory.getLogger("npm")).asInfo())
      .redirectError(Slf4jStream.of(LoggerFactory.getLogger("npm")).asError())
      .exitValueNormal()
      .execute();
  }

  private ProcessExecutor command(String... cmd) {
    return new ProcessExecutor().command(Arrays.asList(cmd));
  }


  private File locate(String... paths) {
    for (String path : paths) {
      File file = new File(path);
      if (file.isFile()) {
        return file.getParentFile();
      }
    }
    throw new IllegalStateException("Could not locate project");
  }

}
