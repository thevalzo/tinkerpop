/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one
 *  * or more contributor license agreements.  See the NOTICE file
 *  * distributed with this work for additional information
 *  * regarding copyright ownership.  The ASF licenses this file
 *  * to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance
 *  * with the License.  You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package org.apache.tinkerpop.gremlin.process.traversal.step.filter.exp;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.Pop;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.Traverser;
import org.apache.tinkerpop.gremlin.process.traversal.step.filter.FilterStep;

import java.util.Optional;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public final class IsOrAllowStep<S> extends FilterStep<S> {

    private final String key;

    public IsOrAllowStep(final Traversal.Admin traversal, final String key) {
        super(traversal);
        this.key = key;
    }

    @Override
    protected boolean filter(final Traverser.Admin<S> traverser) {
        final Optional<S> optional = traverser.getSideEffects().get(this.key);
        if (optional.isPresent())
            return traverser.get().equals(optional.get());
        else {
            final Path path = traverser.path();
            return !path.hasLabel(this.key) || traverser.get().equals(path.getSingle(Pop.head, this.key));
        }

    }
}
