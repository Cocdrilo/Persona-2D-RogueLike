/**
 * The {@code entity} package provides classes and interfaces related to entities in the application.
 * It includes implementations for both players and non-player characters (NPCs), as well as management
 * components for parties and entity statistics.
 *
 * <p>The main classes and interface in this package are:
 * <ul>
 *   <li>{@link entity.Drawable} - An interface for entities that can be drawn or displayed in the application.</li>
 *   <li>{@link entity.Entity} - Represents a generic entity with basic attributes and behaviors.</li>
 *   <li>{@link entity.Entity_stats} - Manages the statistics and attributes of an entity.</li>
 *   <li>{@link entity.NPC} - Represents a non-player character in the application.</li>
 *   <li>{@link entity.partyManager} - Manages the party of entities, including both players and NPCs.</li>
 *   <li>{@link entity.Player} - Represents a player entity in the application.</li>
 *   <li>{@link entity.Player_Prot1} - An example subclass of Player, demonstrating a specific player prototype.</li>
 * </ul>
 *
 * <p>The {@link entity.Drawable} interface is designed for entities that can be visually represented in the application.
 * Classes such as {@link entity.Player} and {@link entity.NPC} may implement this interface to enable drawing or display.
 *
 * <p>Each class and interface in this package contributes to the modeling and management of entities within the application.
 * For detailed information about each class and interface, refer to their respective documentation.
 *
 * @version 1.0
 * @since 2023-11-27
 * @see entity.Drawable
 * @see entity.Entity
 * @see entity.Entity_stats
 * @see entity.NPC
 * @see entity.partyManager
 * @see entity.Player
 * @see entity.Player_Prot1
 */
package entity;
