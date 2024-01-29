# RDF2PG

A java application to transform RDF databases into Property Graph (PG) databases.
rdf2pg implements three transformation methods described in the journal article
"Mapping RDF Databases to Property Graph Databases"
(IEEE Access, May 2020), which is available at [IEEE](https://ieeexplore.ieee.org/document/9088985).

<!-- markdownlint-disable-next-line MD036 -->
**Table of Contents**

- [RDF2PG](#rdf2pg)
  - [Project Description](#project-description)
  - [Repositories](#repositories)
  - [License](#license)
  - [Acknowledgment and Authors](#acknowledgment-and-authors)

## Project Description

This project transforms RDF datasets into a CSV files for futher parsing, mass uploading RDF-like property graph data into RAPSQL Databases, which build on [Postgres](https://www.postgresql.org/) and [Apache AGE](https://age.apache.org/). RAPSQL Databases are in development for possible backend solutions of [OpenSemanticLab](https://github.com/OpenSemanticLab), which builds on [JSON Schema](https://json-schema.org/), [Semantic Media Wiki](https://www.semantic-mediawiki.org/wiki/Semantic_MediaWiki) focuses on Ontology-Interoperability.

## Repositories

- [RAPSQLTranspiler](https://github.com/OpenSemanticWorld/rapsqltranspiler)
- [RAPSQLBench](https://github.com/OpenSemanticWorld/rapsqlbench)

## License

Apache License 2.0

## Acknowledgment and Authors

Thanks to Renzo Angles et al. it is possible to have a basement to develop an essential feature of `RAPSQL`.
It is a fork of [renzoar/rdf2pg](https://github.com/renzoar/rdf2pg/tree/master/src).

Andreas Räder (<https://github.com/raederan>)
