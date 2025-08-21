package org.example.oms.service.business;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.example.common.model.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StateMachineTest {

    static Stream<Arguments> validTransitions() {
        return Stream.of(
                Arguments.of(State.NEW, State.UNACK),
                Arguments.of(State.UNACK, State.LIVE),
                Arguments.of(State.UNACK, State.REJ),
                Arguments.of(State.LIVE, State.FILLED),
                Arguments.of(State.LIVE, State.CXL),
                Arguments.of(State.LIVE, State.REJ),
                Arguments.of(State.FILLED, State.CLOSED),
                Arguments.of(State.CXL, State.CLOSED),
                Arguments.of(State.REJ, State.CLOSED));
    }

    static Stream<Arguments> invalidTransitions() {
        return Stream.of(
                Arguments.of(State.NEW, State.LIVE),
                Arguments.of(State.NEW, State.REJ),
                Arguments.of(State.NEW, State.FILLED),
                Arguments.of(State.NEW, State.CXL),
                Arguments.of(State.NEW, State.CLOSED),
                Arguments.of(State.NEW, State.EXP),
                Arguments.of(State.NEW, State.NEW), // Self transition
                Arguments.of(State.UNACK, State.NEW),
                Arguments.of(State.UNACK, State.UNACK),
                Arguments.of(State.UNACK, State.FILLED),
                Arguments.of(State.UNACK, State.CXL),
                Arguments.of(State.UNACK, State.CLOSED),
                Arguments.of(State.LIVE, State.NEW),
                Arguments.of(State.LIVE, State.UNACK),
                Arguments.of(State.LIVE, State.LIVE),
                Arguments.of(State.LIVE, State.CLOSED), // Direct to closed invalid
                Arguments.of(State.FILLED, State.NEW),
                Arguments.of(State.FILLED, State.LIVE),
                Arguments.of(State.FILLED, State.FILLED),
                Arguments.of(State.CXL, State.NEW),
                Arguments.of(State.CXL, State.LIVE),
                Arguments.of(State.CXL, State.CXL),
                Arguments.of(State.REJ, State.NEW),
                Arguments.of(State.REJ, State.LIVE),
                Arguments.of(State.REJ, State.REJ),
                Arguments.of(State.CLOSED, State.NEW), // From terminal state
                Arguments.of(State.CLOSED, State.CLOSED),
                Arguments.of(State.EXP, State.NEW) // From terminal state
                );
    }

    @ParameterizedTest
    @MethodSource("validTransitions")
    void testIsValidTransition_Valid(State from, State to) {
        assertTrue(
                StateMachine.isValidTransition(from, to),
                "Transition from " + from + " to " + to + " should be valid");
    }

    @ParameterizedTest
    @MethodSource("invalidTransitions")
    void testIsValidTransition_Invalid(State from, State to) {
        assertFalse(
                StateMachine.isValidTransition(from, to),
                "Transition from " + from + " to " + to + " should be invalid");
    }

    @ParameterizedTest
    @MethodSource("validTransitions")
    void testTransition_Valid(State from, State to) {
        Optional<State> result = StateMachine.transition(from, to);
        assertTrue(result.isPresent(), "Valid transition should return non-empty Optional");
        assertEquals(to, result.get(), "Valid transition should return the target state");
    }

    @ParameterizedTest
    @MethodSource("invalidTransitions")
    void testTransition_Invalid(State from, State to) {
        Optional<State> result = StateMachine.transition(from, to);
        assertFalse(result.isPresent(), "Invalid transition should return empty Optional");
    }

    @Test
    void testTransitionFrom() {
        Function<State, Optional<State>> transitionFromLive =
                StateMachine.transitionFrom(State.LIVE);

        // Valid transitions from LIVE
        assertTrue(transitionFromLive.apply(State.FILLED).isPresent());
        assertEquals(State.FILLED, transitionFromLive.apply(State.FILLED).get());
        assertTrue(transitionFromLive.apply(State.CXL).isPresent());
        assertEquals(State.CXL, transitionFromLive.apply(State.CXL).get());
        assertTrue(transitionFromLive.apply(State.REJ).isPresent());
        assertEquals(State.REJ, transitionFromLive.apply(State.REJ).get());

        // Invalid transitions from LIVE
        assertFalse(transitionFromLive.apply(State.NEW).isPresent());
        assertFalse(transitionFromLive.apply(State.UNACK).isPresent());
        assertFalse(transitionFromLive.apply(State.LIVE).isPresent());
        assertFalse(transitionFromLive.apply(State.CLOSED).isPresent());
    }

    @Test
    void testTransitionSequence_ValidFull() {
        Optional<State> result =
                StateMachine.transitionSequence(
                        State.NEW, State.UNACK, State.LIVE, State.FILLED, State.CLOSED);
        assertTrue(result.isPresent());
        assertEquals(State.CLOSED, result.get());
    }

    @Test
    void testTransitionSequence_ValidPartial() {
        Optional<State> result =
                StateMachine.transitionSequence(State.NEW, State.UNACK, State.LIVE);
        assertTrue(result.isPresent());
        assertEquals(State.LIVE, result.get());
    }

    @Test
    void testTransitionSequence_InvalidPartway() {
        // NEW -> UNACK (valid) -> CXL (invalid from UNACK)
        Optional<State> result = StateMachine.transitionSequence(State.NEW, State.UNACK, State.CXL);
        assertFalse(result.isPresent());
    }

    @Test
    void testTransitionSequence_InvalidImmediately() {
        Optional<State> result = StateMachine.transitionSequence(State.NEW, State.LIVE);
        assertFalse(result.isPresent());
    }

    @Test
    void testTransitionSequence_EmptySequence() {
        Optional<State> result =
                StateMachine.transitionSequence(State.NEW); // No transitions provided
        assertTrue(result.isPresent());
        assertEquals(State.NEW, result.get());
    }

    @Test
    void testTransitionSequence_FromTerminalState() {
        Optional<State> result = StateMachine.transitionSequence(State.CLOSED, State.NEW);
        assertFalse(result.isPresent());
    }

    @Test
    void testTransitionSequence_NullInitialState() {
        assertThrows(
                NullPointerException.class,
                () -> StateMachine.transitionSequence(null, State.UNACK),
                "transitionSequence should throw NullPointerException for null initial state");
    }

    @Test
    void testTransitionSequence_WithNullInTransitions() {
        // The transition to a null state should be invalid.
        assertThrows(
                NullPointerException.class,
                () -> StateMachine.transitionSequence(State.NEW, State.UNACK, null),
                "Sequence with a null transition should throw NullPointerException");
    }

    @Test
    void testIsValidTransition_NullFromState() {
        assertFalse(
                StateMachine.isValidTransition(null, State.NEW),
                "isValidTransition should return false for a null 'from' state");
    }

    @Test
    void testIsValidTransition_NullToState() {
        assertFalse(
                StateMachine.isValidTransition(State.NEW, null),
                "isValidTransition should return false for a null 'to' state");
    }

    @Test
    void testIsValidTransition_NullBothStates() {
        assertFalse(
                StateMachine.isValidTransition(null, null),
                "isValidTransition should return false for null 'from' and 'to' states");
    }
}
