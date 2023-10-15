/* 
 * Copyright 2020 Renzo Angles (http://renzoangles.com/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rapsql.rdf2pg.writers;

import de.rapsql.rdf2pg.pgraph.PGEdge;
import de.rapsql.rdf2pg.pgraph.PGNode;

/**
 *
 * @author renzo
 */
public interface PGWriter {
    public void begin();
    public void writeNode(PGNode node);   
    public void writeEdge(PGEdge edge);
    public void end();

}
