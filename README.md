# README

The project is split up into 3 main applications.
- `gui-app`
- `cli-app`
- `demo`

You can run the project with

```
gradle gui-app -q --console=plain
```

Or replacing `gui-app` with any of the above tasks.

For more information run `gradle tasks` to see all the runnable tasks.

## Editor Support

### Eclipse

To import the project in Eclipse:

Click:
- `File -> Import -> Gradle -> Existing Gradle Project`
- `Existing Gradle Project -> Next -> Finish`

To run the project in Eclipse, navigate to:
- `Gradle Tasks -> plant-management-system -> app -> application`
- Under this tab you should see various tasks to run. To run a task,
double click a task (e.g `cli-app`).
- After running a task, this will take you to the `Gradle Executions` tab.
To view the output of the task, click on the console icon on the bottom right.
