# Spring Management API

## Description
The Spring Management API is a RESTful web service designed to manage user accounts for a web application. It provides comprehensive management for various entities, including:

- **Authentication**: Secure user authentication and session management.
- **Users**: Create, retrieve, update, and delete user accounts.
- **Images**: Manage image uploads and retrieval.
- **Appliance Models**: Handle appliance model data and specifications.
- **Inventory**: Track and manage inventory items.
- **Client**: Manage client information and interactions.
- **Historic Appliance**: Retrieve historical data on appliances.
- **Order History**: Access and manage order history records.
- **Order**: Create and manage customer orders.
- **Repair Cost**: Manage repair cost records for various services.
- **Client Payment**: Handle client payment records and transactions.

This structured API enables secure and efficient management of user interactions and transactions across all entities.


## Key Features

- **User Management**: Create, retrieve, update, and delete user accounts.
- **Secure Authentication**: Passwords are securely stored and managed, ensuring user data protection.
- **Validation**: Comprehensive validation for user data, including password strength checks.
- **Error Handling**: Clear and consistent error responses for various failure scenarios.
- **JUnit Testing**: Unit tests for controllers, services, and repositories ensure code reliability and maintainability.
- **Spring Framework**: Built using Spring Boot for rapid development and deployment.
- **Mocking with Mockito**: Unit tests utilize Mockito for mocking dependencies, ensuring isolation of test cases.
- **RESTful Design**: Follows REST principles for a clean and intuitive API structure.

## Technology Stack
- **Java:** The primary programming language used to implement the API.
- **Spring Boot:** Provides the framework for building the RESTful services with dependency injection and other features.
- **Hibernate/JPA:** Used for data persistence and interaction with the database.
- **Mockito:** For creating mock objects and performing unit testing on services and controllers.
- **JUnit:** Framework for writing and executing tests to ensure the functionality and correctness of the API.

## API Endpoints

# Authentication

### 1. Login
- **Method**: `POST`
- **URL**: `/api/auth/login`
- **Headers**: 
  - `Content-Type: application/json`
- **Body**:
{
    "username": "username",
    "password": "password"
}

#### Example
POST /api/auth/login

HTTP/1.1
Content-Type: application/json

{
    "username": "username",
    "password": "password"
}

---

### 2. Logout
- **Method**: `GET`
- **URL**: `/api/auth/logout`

#### Example
GET /api/auth/logout

HTTP/1.1

---

### 3. Verify Role
- **Method**: `GET`
- **URL**: `/api/auth/verify-role`

#### Example
GET /api/auth/verify-role

HTTP/1.1

---

### 4. Health Check
- **Method**: `GET`
- **URL**: `/api/auth/health-check`

#### Example
GET /api/auth/health-check

HTTP/1.1

# Users

### 1. Get All Users
- **Method**: `GET`
- **URL**: `/api/users`

#### Example
GET /api/users

HTTP/1.1

---

### 2. Get User By ID
- **Method**: `GET`
- **URL**: `/api/users/{id}`

#### Example
GET /api/users/52

HTTP/1.1

---

### 3. Create User
- **Method**: `POST`
- **URL**: `/api/users`
- **Headers**: 
  - `Content-Type: application/json`
- **Body**:
{
    "username": "newuser",
    "password": "P@ssw0rd!"
}

#### Example
POST /api/users

HTTP/1.1
Content-Type: application/json

{
    "username": "newuser",
    "password": "P@ssw0rd!"
}

---

### 4. Update Username
- **Method**: `PUT`
- **URL**: `/api/users/{id}/username`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (form data):
  - `newUsername`: `updateduser3`

#### Example
PUT /api/users/3/username?newUsername=updateduser3

HTTP/1.1
Content-Type: application/json

---

### 5. Update Password
- **Method**: `PUT`
- **URL**: `/api/users/{id}/password`
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (form data):
  - `newPassword`: `!assworD1`

#### Example
PUT /api/users/3/password?newPassword=!assworD1

HTTP/1.1
Content-Type: application/json

---

### 6. Delete User
- **Method**: `DELETE`
- **URL**: `/api/users/{id}`

#### Example
DELETE /api/users/52

HTTP/1.1

# Images

### 1. Get All Images
- **Method**: `GET`
- **URL**: `/api/images`

#### Example
GET /api/images

HTTP/1.1

---

### 2. Get Image by ID
- **Method**: `GET`
- **URL**: `/api/images/{id}`

#### Example
GET /api/images/1

HTTP/1.1

---

### 3. Post Image
- **Method**: `POST`
- **URL**: `/api/images`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
{
    "mime": "image/jpeg",
    "name": "example_image",
    "content": "89504E470D0A"
}

#### Example
POST /api/images

HTTP/1.1
Content-Type: application/json

{
    "mime": "image/jpeg",
    "name": "example_image",
    "content": "89504E470D0A"
}

---

### 4. Update Image
- **Method**: `PUT`
- **URL**: `/api/images/{id}`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
{
    "id": 1,
    "mime": "image/png",
    "name": "updated_image",
    "content": "89504E470D0A"
}

#### Example
PUT /api/images/1

HTTP/1.1
Content-Type: application/json

{
    "id": 1,
    "mime": "image/png",
    "name": "updated_image",
    "content": "89504E470D0A"
}

---

### 5. Delete Image by ID
- **Method**: `DELETE`
- **URL**: `/api/images/{id}`

#### Example
DELETE /api/images/1

HTTP/1.1

# Appliance Models

### 1. Get All Appliance Models
- **Method**: GET
- **URL**: /api/appliance-models

Example:
GET /api/appliance-models

---

### 2. Get Appliance Model by ID
- **Method**: GET
- **URL**: /api/appliance-models/{id}

Example:
GET /api/appliance-models/1

---

### 3. Get Appliance Model by Model Name
- **Method**: GET
- **URL**: /api/appliance-models/model/{modelName}

Example:
GET /api/appliance-models/model/Samsung123

---

### 4. Get Appliance Models by Criteria
- **Method**: GET
- **URL**: /api/appliance-models/search
- **Query Parameters**:
  - model: (optional) model name
  - applianceCategory: (optional) appliance category
  - brand: (optional) brand
  - manufactureYear: (optional) manufacture year

Example:
GET /api/appliance-models/search?model=ToshibaRegza&applianceCategory=TV_LCD&brand=LG&manufactureYear=2022

---

### 5. Create a New Appliance Model
- **Method**: POST
- **URL**: /api/appliance-models
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
    "model": "Samsung123",
    "applianceCategory": "TV_LED",
    "brand": "LG",
    "manufactureYear": 2022
}

Example:
POST /api/appliance-models
Content-Type: application/json

{
    "model": "Samsung123",
    "applianceCategory": "TV_LED",
    "brand": "LG",
    "manufactureYear": 2022
}

---

### 6. Update Appliance Model
- **Method**: PUT
- **URL**: /api/appliance-models/{id}
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
    "id": 1,
    "model": "Samsung456",
    "applianceCategory": "TV_OLED",
    "brand": "SONY",
    "manufactureYear": 2023
}

Example:
PUT /api/appliance-models/1
Content-Type: application/json

{
    "id": 1,
    "model": "Samsung456",
    "applianceCategory": "TV_OLED",
    "brand": "SONY",
    "manufactureYear": 2023
}

# Inventory

### 1. Get All Inventory Items
- **Method**: GET
- **URL**: /api/inventory

Example:
GET /api/inventory

---

### 2. Get Inventory Item by ID
- **Method**: GET
- **URL**: /api/inventory/{id}

Example:
GET /api/inventory/1

---

### 3. Get Inventory Items by Serial
- **Method**: GET
- **URL**: /api/inventory/serial/{serial}

Example:
GET /api/inventory/serial/XYZ456

---

### 4. Get Inventory Items by Criteria
- **Method**: GET
- **URL**: /api/inventory/search
- **Query Parameters**:
  - serial: (optional) serial number
  - location: (optional) location of the item
  - lastPrice: (optional) last price of the item
  - modelName: (optional) name of the model
  - component: (optional) component type
  - brand: (optional) brand of the item
  - name: (optional) name of the item

Example:
GET /api/inventory/search?serial=A&location=Shelf A&lastPrice=100&modelName=SonyBravia&component=MAIN_BOARD&brand=LG&name=Panel

---

### 5. Save a New Inventory Item
- **Method**: POST
- **URL**: /api/inventory
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
    "serial": "XYZ456",
    "name": "Main Board",
    "quantityNew": 10,
    "quantityUsed": 5,
    "location": "Shelf A",
    "lastPrice": 50.00,
    "dateLastPrice": "2023-12-01",
    "compatibleApplianceModels": [{"id": 1}],
    "component": "MAIN_BOARD",
    "brand": "LG",
    "images": [{"id": 1}]
}

Example:
POST /api/inventory
Content-Type: application/json

{
    "serial": "XYZ456",
    "name": "Main Board",
    "quantityNew": 10,
    "quantityUsed": 5,
    "location": "Shelf A",
    "lastPrice": 50.00,
    "dateLastPrice": "2023-12-01",
    "compatibleApplianceModels": [{"id": 1}],
    "component": "MAIN_BOARD",
    "brand": "LG",
    "images": [{"id": 1}]
}

---

### 6. Update Inventory Item
- **Method**: PUT
- **URL**: /api/inventory/{id}
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
    "id": 1,
    "serial": "XYZ456",
    "name": "Main Board Updated",
    "quantityNew": 12,
    "quantityUsed": 4,
    "location": "Shelf B",
    "lastPrice": 155.00,
    "dateLastPrice": "2024-05-01",
    "compatibleApplianceModels": [{"id": 2}],
    "component": "MAIN_BOARD",
    "brand": "LG",
    "images": [{"id": 2}]
}

Example:
PUT /api/inventory/1
Content-Type: application/json

{
    "id": 1,
    "serial": "XYZ456",
    "name": "Main Board Updated",
    "quantityNew": 12,
    "quantityUsed": 4,
    "location": "Shelf B",
    "lastPrice": 155.00,
    "dateLastPrice": "2024-05-01",
    "compatibleApplianceModels": [{"id": 2}],
    "component": "MAIN_BOARD",
    "brand": "LG",
    "images": [{"id": 2}]
}

# Client

### 1. Get Clients
- **Method**: GET
- **URL**: /api/clients

Example:
GET /api/clients

---

### 2. Get Client by Identity Card
- **Method**: GET
- **URL**: /api/clients/{identityCard}

Example:
GET /api/clients/1234567891

---

### 3. Get Client by Phone
- **Method**: GET
- **URL**: /api/clients/phone/{phone}

Example:
GET /api/clients/phone/123456789

---

### 4. Get Clients by Partial Name
- **Method**: GET
- **URL**: /api/clients/name/{name}

Example:
GET /api/clients/name/John

---

### 5. Get Clients by Partial Identity Card
- **Method**: GET
- **URL**: /api/clients/identity-card/{partialIdentityCard}

Example:
GET /api/clients/identity-card/1

---

### 6. Post Client
- **Method**: POST
- **URL**: /api/clients
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
    "identityCard": 1234567891,
    "name": "Alex Diaz",
    "phone": 1234567891,
    "address": "123 Main St, Springfield, USA"
}

Example:
POST /api/clients
Content-Type: application/json

{
    "identityCard": 1234567891,
    "name": "Alex Diaz",
    "phone": 1234567891,
    "address": "123 Main St, Springfield, USA"
}

---

### 7. Update Client
- **Method**: PUT
- **URL**: /api/clients/{identityCard}
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
    "identityCard": 123456789,
    "name": "Alex Diaz",
    "phone": 1234567890,
    "address": "123 Main St, Springfield, USA"
}

Example:
PUT /api/clients/123456789
Content-Type: application/json

{
    "identityCard": 123456789,
    "name": "Alex Diaz",
    "phone": 1234567890,
    "address": "123 Main St, Springfield, USA"
}

# Historic Appliance

### 1. Get Historic Appliances
- **Method**: GET
- **URL**: /api/historic-appliances

Example:
GET /api/historic-appliances

---

### 2. Get Historic Appliance by Serial
- **Method**: GET
- **URL**: /api/historic-appliances/{serial}

Example:
GET /api/historic-appliances/XYZ456

---

### 3. Get Historic Appliances by Model
- **Method**: GET
- **URL**: /api/historic-appliances/model/{modelName}

Example:
GET /api/historic-appliances/model/Samsung456

---

### 4. Get Historic Appliances by Partial Serial
- **Method**: GET
- **URL**: /api/historic-appliances/serial?serial={partial_serial}

Example:
GET /api/historic-appliances/serial?serial=SER

---

### 5. Get Historic Appliances by Partial Model
- **Method**: GET
- **URL**: /api/historic-appliances/model-containing?modelName={partial_modelName}

Example:
GET /api/historic-appliances/model-containing?modelName=Samsung

---

### 6. Post Appliance Model
- **Method**: POST
- **URL**: /api/historic-appliances
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
    "serial": "XYZ456",
    "model": {
        "id": "1"
    },
    "manufactureDate": "2021-01-01"
}

Example:
POST /api/historic-appliances
Content-Type: application/json

{
    "serial": "XYZ456",
    "model": {
        "id": "1"
    },
    "manufactureDate": "2021-01-01"
}

---

### 7. Update Appliance Model
- **Method**: PUT
- **URL**: /api/historic-appliances/{serial}
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
    "serial": "XYZ456",
    "model": {
        "id": "1"
    },
    "manufactureDate": "2021-01-10"
}

Example:
PUT /api/historic-appliances/XYZ456
Content-Type: application/json

{
    "serial": "XYZ456",
    "model": {
        "id": "1"
    },
    "manufactureDate": "2021-01-10"
}

# Order History

### 1. Get All Order Histories
- **Method**: GET
- **URL**: /api/order-history

Example:
GET /api/order-history

---

### 2. Get Order History by ID
- **Method**: GET
- **URL**: /api/order-history/{id}

Example:
GET /api/order-history/1

---

### 3. Get Order Histories by Order ID
- **Method**: GET
- **URL**: /api/order-history/order/{orderId}

Example:
GET /api/order-history/order/1

---

### 4. Create Order History
- **Method**: POST
- **URL**: /api/order-history
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
  "order": {
    "id": 1
  },
  "text": "Order placed successfully"
}

Example:
POST /api/order-history
Content-Type: application/json

{
  "order": {
    "id": 1
  },
  "text": "Order placed successfully"
}

---

### 5. Update Order History
- **Method**: PUT
- **URL**: /api/order-history/{id}
- **Headers**:
  - Content-Type: application/json
- **Body**:
{
  "id": 1,
  "order": {
    "id": 1
  },
  "eventDate": "2024-05-01",
  "text": "Order confirmed"
}

Example:
PUT /api/order-history/1
Content-Type: application/json

{
  "id": 1,
  "order": {
    "id": 1
  },
  "eventDate": "2024-05-01",
  "text": "Order confirmed"
}

# Order

### 1. Get All Orders
- **Method**: GET
- **URL**: /api/orders

Example:
GET /api/orders

---

### 2. Get Orders Pageable
- **Method**: GET
- **URL**: /api/orders/pageable?page=2&size=5&sortBy=id&sortDirection=desc

Example:
GET /api/orders/pageable?page=2&size=5&sortBy=id&sortDirection=desc

---

### 3. Get Last Orders
- **Method**: GET
- **URL**: /api/orders/last

Example:
GET /api/orders/last

---

### 4. Get Order by ID
- **Method**: GET
- **URL**: /api/orders/{id}

Example:
GET /api/orders/1

---

### 5. Get Order by Reference Code
- **Method**: GET
- **URL**: /api/orders/reference/{referenceCode}

Example:
GET /api/orders/reference/00816574-3665-4546-8da6-439ffc315713

---

### 6. Get Orders by Criteria
- **Method**: GET
- **URL**: /api/orders/search?warranty=false
- **Query Parameters**:
  - id (optional)
  - clientIdentityCard (optional)
  - historicApplianceSerial (optional)
  - status (optional)
  - createdDate (optional)
  - warranty (required)

Example:
GET /api/orders/search?warranty=false

Body (if needed for criteria):
{
    "model": "Samsung123",
    "applianceCategory": "TV_LED",
    "brand": "LG",
    "manufactureYear": 2022
}

---

### 7. Post Order
- **Method**: POST
- **URL**: /api/orders
- **Body**:
{
    "client": {
        "identityCard": "222222222"
    },
    "issue": "Screen not working",
    "productReceivedNotes": "Received with scratches",
    "historicAppliance": {
        "serial": "SER123"
    },
    "images": [
        {
            "id": 27
        }
    ],
    "status": "PENDENT_FOR_COMPONENT",
    "warranty": "true"
}

Example:
POST /api/orders
Content-Type: application/json

{
    "client": {
        "identityCard": "222222222"
    },
    "issue": "Screen not working",
    "productReceivedNotes": "Received with scratches",
    "historicAppliance": {
        "serial": "SER123"
    },
    "images": [
        {
            "id": 27
        }
    ],
    "status": "PENDENT_FOR_COMPONENT",
    "warranty": "true"
}

---

### 8. Update Order Status
- **Method**: PATCH
- **URL**: /api/orders/{id}/status?status=PENDENT_FOR_COMPONENT

Example:
PATCH /api/orders/1/status?status=PENDENT_FOR_COMPONENT

# Repair Cost

### 1. Get All Repair Costs
- **Method**: GET
- **URL**: /api/repair-costs

Example:
GET /api/repair-costs

---

### 2. Get Repair Cost by ID
- **Method**: GET
- **URL**: /api/repair-costs/{id}

Example:
GET /api/repair-costs/1

---

### 3. Get Repair Costs by Order
- **Method**: GET
- **URL**: /api/repair-costs/order?orderId={{order_id}}
- **Query Parameter**:
  - orderId (required)

Example:
GET /api/repair-costs/order?orderId=1

---

### 4. Get Repair Costs by Amount
- **Method**: GET
- **URL**: /api/repair-costs/amount?amount=150
- **Query Parameter**:
  - amount (required)

Example:
GET /api/repair-costs/amount?amount=150

---

### 5. Create a New Repair Cost
- **Method**: POST
- **URL**: /api/repair-costs
- **Body**:
{
    "amount": 100.00,
    "description": "Repair description",
    "order": {
        "id": 1
    }
}

Example:
POST /api/repair-costs
Content-Type: application/json

{
    "amount": 100.00,
    "description": "Repair description",
    "order": {
        "id": 1
    }
}

---

### 6. Update an Existing Repair Cost
- **Method**: PUT
- **URL**: /api/repair-costs/{id}
- **Body**:
{
    "id": 1,
    "amount": 200,
    "description": "Updated repair description",
    "order": {
        "id": 1
    }
}

Example:
PUT /api/repair-costs/1
Content-Type: application/json

{
    "id": 1,
    "amount": 200,
    "description": "Updated repair description",
    "order": {
        "id": 1
    }
}

# Client Payment

### 1. Get Client Payments
- **Method**: GET
- **URL**: /api/client-payments

Example:
GET /api/client-payments

---

### 2. Get Client Payment by ID
- **Method**: GET
- **URL**: /api/client-payments/{id}

Example:
GET /api/client-payments/1

---

### 3. Get Client Payments by Order
- **Method**: GET
- **URL**: /api/client-payments/by-order/{orderId}

Example:
GET /api/client-payments/by-order/1

---

### 4. Get Client Payments by Date
- **Method**: GET
- **URL**: /api/client-payments/by-date?date={{date}}
- **Query Parameter**:
  - date (required)

Example:
GET /api/client-payments/by-date?date=2024-05-01

---

### 5. Post Client Payment
- **Method**: POST
- **URL**: /api/client-payments
- **Body**:
{
    "amount": 100.00,
    "order": {
        "id": 1
    }
}

Example:
POST /api/client-payments
Content-Type: application/json

{
    "amount": 100.00,
    "order": {
        "id": 1
    }
}

---

### 6. Update Client Payment
- **Method**: PUT
- **URL**: /api/client-payments/{id}
- **Body**:
{
    "id": 1,
    "amount": 150.00,
    "date": "2024-05-02",
    "order": {
        "id": 1
    }
}

Example:
PUT /api/client-payments/1
Content-Type: application/json

{
    "id": 1,
    "amount": 150.00,
    "date": "2024-05-02",
    "order": {
        "id": 1
    }
}

## Validation Rules

## Testing Strategy
The project includes extensive unit tests that cover the key functionalities of the API, ensuring that edge cases are handled and that the code is robust against changes.
