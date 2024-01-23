# rdf2pg

A java application to transform RDF databases into Property Graph (PG) databases.
rdf2pg implements three transformation methods described in the journal article
"Mapping RDF Databases to Property Graph Databases"
(IEEE Access, May 2020), which is available at [IEEE](https://ieeexplore.ieee.org/document/9088985).

<!-- markdownlint-disable-next-line MD036 -->
**Table of Contents**

- [rdf2pg](#rdf2pg)
  - [Project Description](#project-description)
  - [Primary Branches](#primary-branches)
  - [Authors and Acknowledgment](#authors-and-acknowledgment)
  - [License](#license)

## Project Description

This project transforms RDF datasets into a CSV files for futher parsing and mass uploading rdf-like property graph data into [rapsql databases](https://github.com/OpenSemanticLab/rapsql), which build on [Postgres](https://www.postgresql.org/) and [Apache AGE](https://age.apache.org/). A possible use case for [OpenSemanticLab](https://github.com/OpenSemanticLab).

## Primary Branches

- `main` -- Basic modifications from forked repository to briefly inform about the project.
- `maven-migration` -- Maven migration for dependency management of `RAPSQLTranspiler` and Testing following the YARS property graph model.
- `maven-rdfid` -- Maven migration for dependency management of `RAPSQLTranspiler` and Testing following a generalized property name called `rdfid` within the property graph model.
- `yars` -- Distribution of `RDF2PG` in combination with `RAPSQLTranspiler` for `RAPSQLBench` testing using the YARS property graph model.
- `rdfid` -- Distribution of `RDF2PG` in combination with `RAPSQLTranspiler` for `RAPSQLBench` testing using the generalized property name `rdfid` within the property graph model.

## Authors and Acknowledgment

Thanks to Prof. Renzo Angles et al. it is possible to have a basement to develop an important feature of `rapsql`.
It is a fork of [renzoar/rdf2pg](https://github.com/renzoar/rdf2pg/tree/master/src).

## License

This project is licenced under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
