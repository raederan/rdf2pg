# RDF2PG

A java application to transform RDF databases into Property Graph (PG) databases.
rdf2pg implements three transformation methods described in the journal article
"Mapping RDF Databases to Property Graph Databases"
(IEEE Access, May 2020), which is available at [IEEE](https://ieeexplore.ieee.org/document/9088985).

<!-- markdownlint-disable-next-line MD036 -->
**Table of Contents**

- [RDF2PG](#rdf2pg)
  - [Project Description](#project-description)
  - [Maven Migration](#maven-migration)
  - [Repositories](#repositories)
  - [License](#license)
  - [Acknowledgment and Authors](#acknowledgment-and-authors)

## Project Description

This project provides syntactic data interoperability for rapsqltranspiler in yars format. Furthermore it parses and uploads RDF-like property graphs to RAPSQL databases built on [Postgres](https://www.postgresql.org/) and [Apache AGE](https://age.apache.org/). RAPSQL databases are under development for possible backend solutions of [OpenSemanticLab](https://github.com/OpenSemanticLab), which builds on [JSON Schema](https://json-schema.org/) and [Semantic Media Wiki](https://www.semantic-mediawiki.org/wiki/Semantic_MediaWiki) with a focus on semantic applications.

## Maven Migration

1. Create new Java project using maven-archetype-quickstart
2. Configure `pom.xml` and add dependencies
3. Move `src` to `src/main/java/rapsql/rdf2pg`
4. Move `test` to `src/test/java/rapsql/rdf2pg`
5. Add and change `import` and `package` statements for each java file

## Repositories

- [RAPSQLTranspiler](https://github.com/OpenSemanticWorld/rapsqltranspiler)
- [RAPSQLBench](https://github.com/OpenSemanticWorld/rapsqlbench)

## License

Apache License 2.0

## Acknowledgment and Authors

Thanks to Angles et al. it is possible to have a basis for an essential component of RAPSQL. It is a fork of [renzoar/rdf2pg](https://github.com/renzoar/rdf2pg/tree/master/src).

Andreas Räder (<https://github.com/raederan>)
