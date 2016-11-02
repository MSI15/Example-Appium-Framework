$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("exampleFeature.feature");
formatter.feature({
  "line": 2,
  "name": "Example test",
  "description": "",
  "id": "example-test",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@example"
    }
  ]
});
formatter.before({
  "duration": 24506424087,
  "status": "passed"
});
formatter.before({
  "duration": 45445,
  "status": "passed"
});
formatter.scenario({
  "line": 5,
  "name": "Test",
  "description": "",
  "id": "example-test;test",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 4,
      "name": "@ios"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "I am on the device settings screen",
  "keyword": "Given "
});
formatter.match({
  "location": "ExampleStepDef.i_on_the_device_settings()"
});
formatter.result({
  "duration": 1918852234,
  "status": "passed"
});
formatter.after({
  "duration": 12673865957,
  "status": "passed"
});
});