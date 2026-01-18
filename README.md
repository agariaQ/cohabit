# TODO
## MUST
* [X] **M1:** The backend (BE) of the system must be an individual component.
* [X] **M2:** The frontend (FE) of the system must be an individual component implemented using HTML5, CSS and JavaScript.
* [X] **M3:** The communication between FE and BE components must be implemented using HTTP(S).
* [X] **M4:** The communication between FE and BE components must be implemented using asynchronous data transfer (AJAX).
* [X] **M5:** The HTTP endpoints of the BE component must return the data either as JSON or as XML.
* [X] **M6:** The HTTP endpoints of the BE component must manage resources using HTTP methods GET, POST, PUT and DELETE (each method used by at least one endpoint).
* [X] **M7:** The HTTP endpoints of the FE component must consume resources using HTTP methods GET, POST, PUT and DELETE from at least one endpoint.
* [X] **M8:** The system must consume at least one external REST web service.
* [X] **M9:** The system must implement session management (e.g., login, sessionID, JWT).

## SHOULD
* [ ] **S1:** The system should consume at least two external REST web services.
* [ ] **S2:** The system should offer a second individual FE component that communicates with at least three HTTP endpoints of the BE component.
* [ ] **S3:** The FE component should be designed in a way that it is W3C compliant ([https://validator.w3.org/](https://validator.w3.org/)).
* [ ] **S4:** The FE component should be responsive, with dedicated views for mobile and desktop screens.

## COULD
* [ ] **C1:** The system could consume at least three external REST web services.q
* [ ] **C2:** The HTTP endpoints of the BE component could return data as both JSON and XML.
* [ ] **C3:** The BE component of the system could provide a PATCH HTTP endpoint, which is consumed by the FE component.


