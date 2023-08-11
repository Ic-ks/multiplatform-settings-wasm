/*
 * Copyright 2019 Russell Wolf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.russhwolf.settings

import kotlinx.browser.localStorage
import org.w3c.dom.Storage
import org.w3c.dom.get
import org.w3c.dom.set

/**
 * A collection of storage-backed key-value data
 *
 * This class allows storage of values with the [Int], [Long], [String], [Float], [Double], or [Boolean] types, using a
 * [String] reference as a key. Values will be persisted across app launches.
 *
 * The specific persistence mechanism is defined using a platform-specific implementation, so certain behavior may vary
 * across platforms. In general, updates will be reflected immediately in-memory, but will be persisted to disk
 * asynchronously.
 *
 * Operator extensions are defined in order to simplify usage. In addition, property delegates are provided for cleaner
 * syntax and better type-safety when interacting with values stored in a `Settings` instance.
 *
 * On the JS platform, this class can be created by passing a [Storage] instance which will be used as a delegate. By
 * default [localStorage] will be used if nothing is provided.
 *
 * Unlike the implementations on Android and iOS, `StorageSettings` does not include a [Settings.Factory] because
 * the `Storage` API does not provide a way to create multiple named instances.
 */

// Prevent delegate.["KEY"] and use delegate.getItem("KEY") access see: https://youtrack.jetbrains.com/issue/KT-59294
public class StorageSettings(private val delegate: Storage = localStorage) : Settings {

    public override val keys: Set<String> get() = List(size) { delegate.key(it)!! }.toSet()
    public override val size: Int get() = delegate.length

    public override fun clear(): Unit = delegate.clear()
    public override fun remove(key: String): Unit = delegate.removeItem(key)
    public override fun hasKey(key: String): Boolean = delegate.getItem(key) != null

    public override fun putInt(key: String, value: Int) {
        delegate.setItem(key, value.toString())
    }

    public override fun getInt(key: String, defaultValue: Int): Int =
        delegate.getItem(key)?.toInt() ?: defaultValue

    public override fun getIntOrNull(key: String): Int? =
        delegate.getItem(key)?.toIntOrNull()

    public override fun putLong(key: String, value: Long) {
        delegate.setItem(key, value.toString())
    }

    public override fun getLong(key: String, defaultValue: Long): Long =
        delegate.getItem(key)?.toLong() ?: defaultValue

    public override fun getLongOrNull(key: String): Long? =
        delegate.getItem(key)?.toLongOrNull()


    public override fun putString(key: String, value: String) {
        delegate.setItem(key, value)
    }

    public override fun getString(key: String, defaultValue: String): String = delegate.getItem(key) ?: defaultValue

    public override fun getStringOrNull(key: String): String? = delegate.getItem(key)

    public override fun putFloat(key: String, value: Float) {
        delegate.setItem(key, value.toString())
    }

    public override fun getFloat(key: String, defaultValue: Float): Float =
        delegate.getItem(key)?.toFloat() ?: defaultValue

    public override fun getFloatOrNull(key: String): Float? =
        delegate.getItem(key)?.toFloatOrNull()

    public override fun putDouble(key: String, value: Double) {
        delegate.setItem(key, value.toString())
    }

    public override fun getDouble(key: String, defaultValue: Double): Double =
        delegate.getItem(key)?.toDouble() ?: defaultValue

    public override fun getDoubleOrNull(key: String): Double? =
        delegate.getItem(key)?.toDoubleOrNull()

    public override fun putBoolean(key: String, value: Boolean) {
        delegate.setItem(key, value.toString())
    }

    public override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        delegate.getItem(key)?.toBoolean() ?: defaultValue

    public override fun getBooleanOrNull(key: String): Boolean? =
        delegate.getItem(key)?.toBoolean()
}
