# CarAdvert Web Service on Play! Framework

### Setup

Backend: Play 2.4

Database: Mongo

1. Install activator from [here](https://www.lightbend.com/activator/download)
2. Follow instructions to add it to your `PATH` [here](https://playframework.com/documentation/2.4.x/Installing)
3. Install mongodb
   - Mac: `brew install mongodb`
    - Linux: Follow instructions [here](https://docs.mongodb.com/v3.0/administration/install-on-linux/)
4. Run mongo on localhost with default port (27017) and designated datastore folder for the project
    - `mongod --dbpath <path>/<to>/<project>/datastore`
5. Run the server on localhost:9000 with
    - `activator run`
6. Run tests with
    - `activator test`

### REST endpoints

On http://localhost:9000/

| Endpoint      | Verb | Notes  |
| ------------- |:----:| -----------------------------------------------------------------------------------:|
| get(id)       | GET  |   The variable id needs to be a string with of a UUID format. Returns the carAdvert if found, otherwise null   |
| getAll        | GET  |   Returns a JSON array of carAdverts. Optional query string variable "sortBy={id, title, fuel, price, isNew, mileage, firstRegistration}" |
| create        | POST |    Accepts a properly structured JSON object for a CarAdvert, without the id field, creates it and returns it back with the created id field |
| update        | POST |    Accepts a properly structured JSON object for a CarAdvert, with the existing id field, updates it and returns it back |
| delete(id)    | POST |    The variable id needs to be a string with a UUID format. Returns the deleted CarAdvert if found and deleted, otherwise null|



### Known Issues
- Routes aren't validated, please ensure JSON object sent to `create` endpoint is of the form:
```
{
	"title": String,
	"fuel": String | {"gasoline", "diesel"},
	"price": Int,
	"isNew": Boolean,
	"mileage": Integer,
	"firstRegistration": String of "yyyy-mm-dd"
}
```

e.g.

```
{
	"title":"Audi A4",
	"fuel":"gasoline",
	"price":0,
	"isNew":false,
	"mileage":10000,
	"firstRegistration":"1990-02-01"
}
```
### Also note
 - new cars also require mileage and firstRegistration fields
 - update carAdvert object must contain a valid id value due to current lack of validation

### Use Postman to manually test API
A postman collection is included at the project root: `AutoScout.postman_collection.json`

To use:

1. Download postman [here](https://www.getpostman.com/)
2. Import the collection (click postman's `Import` button at top left)
3. Run database and server with default (local) settings
3. Choose the create postman route and hit `Send`
4. Copy the UUID of the newly created CarAdvert to append to the `update` and/or `delete` endpoints
5. Enjoy!

