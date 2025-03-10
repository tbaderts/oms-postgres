package org.example.oms.service.business;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.example.oms.model.State;

public class StateMachine {
    // Define valid transitions as immutable maps
    private static final Map<State, Set<State>> VALID_TRANSITIONS = createTransitionMap();

    private static Map<State, Set<State>> createTransitionMap() {
        Map<State, Set<State>> transitions = new EnumMap<>(State.class);
        transitions.put(State.NEW, Set.of(State.UNACK));
        transitions.put(State.UNACK, Set.of(State.LIVE, State.REJ));
        transitions.put(State.LIVE, Set.of(State.FILLED, State.CXL, State.REJ));
        transitions.put(State.FILLED, Set.of(State.CLOSED));
        transitions.put(State.CXL, Set.of(State.CLOSED));
        transitions.put(State.REJ, Set.of(State.CLOSED));
        transitions.put(State.CLOSED, Collections.emptySet());
        transitions.put(State.EXP, Collections.emptySet());

        return Collections.unmodifiableMap(transitions);
    }

    // Pure function to validate a transition
    public static boolean isValidTransition(State from, State to) {
        return Optional.ofNullable(VALID_TRANSITIONS.get(from))
                .map(validStates -> validStates.contains(to))
                .orElse(false);
    }

    // Function to transition state, returning Optional to handle invalid
    // transitions
    public static Optional<State> transition(State from, State to) {
        return isValidTransition(from, to) ? Optional.of(to) : Optional.empty();
    }

    // Higher-order function that returns a transition function for a specific
    // current state
    public static Function<State, Optional<State>> transitionFrom(State from) {
        return to -> transition(from, to);
    }

    // Composition of transitions
    public static Optional<State> transitionSequence(State initial, State... transitions) {
        Optional<State> current = Optional.of(initial);

        for (State nextState : transitions) {
            current = current.flatMap(state -> transition(state, nextState));
        }

        return current;
    }
}
