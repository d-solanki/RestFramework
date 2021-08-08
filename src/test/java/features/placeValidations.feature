#Author: Deepak Solanki
Feature: Validating Place API's

@AddPlace @all @regression
Scenario Outline: Verify if Place is being successfully added in AddPlaceAPI
Given Add Place payload with "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "POST" http request
Then the API call got success with status code 200 
And "status" in response body is "OK"
And "scope" in response body is "APP"
Then place_id should be stored in maps
When user calls "GetPlaceAPI" with "GET" http request
Then the API call got success with status code 200
And "name" in response body is "<name>"


Examples:
|name|language|address|
|DSHouse|Hindi|Gaur City-2, Noida|

@DeletePlace @all
Scenario: Delete place
Given Delete Place payload
When  user calls "DeletePlaceAPI" with "POST" http request
Then the API call got success with status code 200