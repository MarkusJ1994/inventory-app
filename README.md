# inventory-app

Work in progress to convert a basic CRUD app into an event-driven application and evaluate complexity and benefits.

Folder structure for java backend:

src
- configuration
- controller
- domain
  - exceptions
  - events
  - aggregator
  - services
  - \<state area>
    - event
    - aggregator
    - dto
    - data
    - services

Simple run docker-compose build and up for easy setup of db, backend and frontend.
Then visit localhost:3000 in your browser

