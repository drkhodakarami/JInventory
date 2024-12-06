/***********************************************************************************
 * Copyright (c) 2024 Alireza Khodakarami (Jiraiyah)                               *
 * ------------------------------------------------------------------------------- *
 * MIT License                                                                     *
 * =============================================================================== *
 * Permission is hereby granted, free of charge, to any person obtaining a copy    *
 * of this software and associated documentation files (the "Software"), to deal   *
 * in the Software without restriction, including without limitation the rights    *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 * copies of the Software, and to permit persons to whom the Software is           *
 * furnished to do so, subject to the following conditions:                        *
 * ------------------------------------------------------------------------------- *
 * The above copyright notice and this permission notice shall be included in all  *
 * copies or substantial portions of the Software.                                 *
 * ------------------------------------------------------------------------------- *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR      *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,        *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE     *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER          *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,   *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE   *
 * SOFTWARE.                                                                       *
 ***********************************************************************************/

package jiraiyah.jinventory.interfaces;

import jiraiyah.jinventory.WrappedInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

/**
 * An interface that provides a contract for block entities that manage wrapped inventories.
 * This interface extends {@link IWrappedInventory} with a specific focus on handling
 * output inventories, allowing for the addition, retrieval, and management of inventories
 * associated with block entities.
 * <p>
 * Implementations of this interface are expected to provide mechanisms for adding output
 * inventories, retrieving wrapped inventory storage, and accessing inventory storage
 * based on directional orientation. This facilitates seamless integration with inventory
 * management systems and interactions with neighboring blocks or entities.
 * </p>
 * <p>
 * The interface defines constants for default output inventory index, size, and direction,
 * which can be utilized or overridden by implementing classes to customize inventory behavior.
 * </p>
 * <p>
 * This interface is part of the Jiraiyah library and is designed to work with the
 * organization's specific modules, packages, and libraries, ensuring consistency and
 * adherence to internal best practices.
 * </p>
 */
@SuppressWarnings("unused")
public interface IWrappedInventoryProvider extends IWrappedInventory
{
    /**
     * Adds an output inventory to this block entity.
     * <p>
     * This method initializes an output inventory of the specified size, allowing
     * items to be extracted or transferred from the block entity in the specified
     * direction. The output inventory facilitates item management and interactions
     * with neighboring blocks or entities.
     * </p>
     *
     * @param size     The size (capacity) of the output inventory. This determines
     *                 how many items the inventory can hold.
     * @param direction The direction from which items can be extracted from the
     *                  output inventory. This parameter can restrict access to the
     *                  inventory from a specific direction (e.g., NORTH, SOUTH).
     *                  If set to null, the inventory may be accessible from all
     *                  directions.
     */
    void addOutputInventory(int size, Direction direction);

    /**
     * Retrieves the wrapped inventory storage of this block entity.
     * The inventory storage contains all inventories associated with the
     * block entity and allows for various inventory operations.
     *
     * @return The WrappedInventoryStorage instance containing the block entity's inventory.
     */
    WrappedInventoryStorage getInventory();

    /**
     * Retrieves the storage for the inventory associated with this block entity
     * in the specified direction. This method allows for accessing the inventory
     * storage in relation to the block entity's orientation, enabling proper
     * interactions with neighboring blocks or entities.
     *
     * @param direction The direction from which the inventory storage is being accessed.
     *                  If null, the storage for all directions may be considered.
     *
     * @return The InventoryStorage instance for the specified direction, or null if
     * no inventory storage exists in that direction.
     */
    InventoryStorage getInventoryStorage(@Nullable Direction direction);

    /**
     * Retrieves the output inventory of this block entity. The output inventory is
     * specifically designated for holding items that can be extracted or transferred
     * from the block entity. If the inventory has been initialized and contains output
     * storage, this method returns the corresponding SimpleInventory; otherwise,
     * it returns null.
     *
     * @return The SimpleInventory instance representing the output inventory, or
     * null if no output inventory exists or is initialized.
     */
    SimpleInventory getOutputInventory();
}