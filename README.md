# Sanctions Search

Thank you for taking the time to complete our code challenge!

For this problem, you will build an application for searching publicly available sanction data. You may take as long as you like with your solution.

## Requirements

Your solution:
0. may use any language, libraries, database, or frameworks
1. must be dockerized.
   - Please update [Dockerfile](Dockerfile) and [docker-compose.yml](./docker-compose.yml) as needed.
2. must pass the [smoke-tests](#Testing)
3. must contain an [api](#API) and [ui](#UI)

### Testing

We provide a smokescreen test that can be run against your api with `make smoke-test`.

The api is stubbed with fixtures that pass all the tests for a sanity check. 
If `make smoke-test` does not work for you and you believe you have `docker` and `docker-compose` installed and up-to-date, please let us know.

To ensure the smoke-test works properly, make sure you update the value of `API` for the `test` service in [docker-compose.yml](./docker-compose.yml).

We may run additional test cases against your solution.


## API
Your server should have a `/search` endpoint that takes a `name` query parameter with a person's name. It should respond with an array of matches with the following shape:
```json
{
  "name": "Kim Jung Un",
  "aliases": ["Rocket Man"],
  "sanctioned": true,
  "list": "european_sanctions_list",
  "relevance": 0.92,
}
```

Relevance should be a float in the range [0,1] indicating how close the result is to the users search. relevance=1 indicates an exact string match between the search and either the name or one of the aliases. 

### Bootstrapping the Sanctions Data
European Sanctions data is publicly available with an [EU Login](https://webgate.ec.europa.eu/cas/login?loginRequestId=ECAS_LR-5816125-a3R1xh1RhIp0ZBXrLAZHmwuuL5f3jUR3W2nf3cLoeaIetewOSzQ6gkC1LBG07tG9c4ZYd0PHLOwCoXGK4Nbj38-rS0vSrmBGYCtzg7YLRrbJx-n9FPWblzzJ1zdy1GE5ysZ6saqwZ5zxHJwZI6V5A9ZYoo). After you have created an account. You can navigate [here](https://webgate.ec.europa.eu/europeaid/fsd/fsf#!/files) to acquire a link to the sanctions data. You will have the choice of CSV, XML, or PDF data. Make sure to click `Show Settings for Crawler/Robot` expander to get the correct link. Since the data is unlikely to change during the course of this challenge, you can just bootstrap it once on startup.

In order to communicate to the smoke-test when the server is ready, create a /status endpoint that returns an error code until the bootstrapping is complete and the server is ready to serve requests.

## UI
Make a simple UI for running searches against your API. Doesn't need to be a pretty, single-page application; just show us how you build something simple but useful.
