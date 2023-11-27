/**
 * The {@code negotiation} package provides classes for managing negotiation systems and handling negotiation-related functionality.
 * It includes components for negotiation management, systems, questions, and options.
 *
 * <p>The main classes in this package are:
 * <ul>
 *   <li>{@link negotiation.NegotiationManager} - Manages the overall negotiation process and interactions.</li>
 *   <li>{@link negotiation.NegotiationSystem} - Implements the core logic for a negotiation system in the application.</li>
 *   <li>{@link negotiation.Opcion} - Represents an option in a negotiation question, providing choices for negotiation responses.</li>
 *   <li>{@link negotiation.Pregunta} - Represents a question in a negotiation, including options and possible outcomes.</li>
 *   <li>{@link negotiation.PreguntaJson} - Represents a question in a negotiation loaded from JSON data.</li>
 * </ul>
 *
 * <p>{@link negotiation.NegotiationManager} serves as a central component for managing the negotiation process,
 * handling interactions, and coordinating negotiations between different entities in the game.
 *
 * <p>{@link negotiation.NegotiationSystem} implements the core logic for a negotiation system,
 * providing methods and functionality for negotiating with in-game entities.
 *
 * <p>{@link negotiation.Opcion} represents an option in a negotiation question, allowing for different responses or choices
 * that the player or other entities can make during a negotiation.
 *
 * <p>{@link negotiation.Pregunta} represents a question in a negotiation, including options and possible outcomes.
 * It encapsulates the structure of a negotiation question and provides methods for handling responses.
 *
 * <p>{@link negotiation.PreguntaJson} represents a question in a negotiation loaded from JSON data.
 * It allows for dynamic loading of negotiation questions from external sources.
 *
 * <p>Each class in this package contributes to the modeling and management of negotiations within the application.
 * For detailed information about each class, refer to their respective documentation.
 *
 * @version 1.0
 * @since 2023-11-27
 * @see negotiation.NegotiationManager
 * @see negotiation.NegotiationSystem
 * @see negotiation.Opcion
 * @see negotiation.Pregunta
 * @see negotiation.PreguntaJson
 */
package negotiation;
