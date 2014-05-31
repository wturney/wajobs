WaJobs
======

A quick project evaluating Spring, Jackson, JPA and Hibernate. Particularly focused on the following:

* Ease-of-testing at various layers. 
* Java/Annotation configuration as an alternative to XML.


The goal was to provide the foundations of a REST service for aggregating job postings from the various jurisdictions throughout Washington. It should be noted that the resulting project is very over-engineered given its limited scope. This was done intentionally in an effort to simulate a more realistic EE architecture.

The code is divided into the following groups

* REST API <code>com.wtl.wawork.rest.*</code>
* HTTP Client <code>com.wtl.wawork.web.*</code>
* Services <code>com.wtl.wawork.core.service.*</code>
* Repositories <code>com.wtl.wawork.core.persistence.*</code>

HTTP Client
===
An AngularJS client example is [available here](https://wajobs.hightower-software.com/client/postings).


REST API
===

### Jurisdictions

| Endpoint | Description |
| ---- | --------------- |
| [GET /jurisdictions](https://wajobs.hightower-software.com/jurisdictions) | Get a list of jurisdictions. |

Jurisdiction endpoints require authorization

| Username | Password |
| ---- | --------------- |
| spider | man |



### Postings

| Endpoint | Description |
| ---- | --------------- |
| [GET /postings](https://wajobs.hightower-software.com/postings) | Get a list of postings |

#### Posting Filter Parameters

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Required</th>
            <th width="50">Type</th>
            <th width=100%>Description</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><code>j</code></td>
            <td>optional</td>
            <td>integer list</td>
            <td>Comma separated list of jurisdiction IDs to filter postings</td>
        </tr>
        <tr>
            <td><code>et</code></td>
            <td>optional</td>
            <td>integer</td>
            <td>Posting employment type ID</td>
        </tr>
    </tbody>
</table>

Special thanks to the maintainers of the https://github.com/spring-guides repositories for providing an excellent reference project.
