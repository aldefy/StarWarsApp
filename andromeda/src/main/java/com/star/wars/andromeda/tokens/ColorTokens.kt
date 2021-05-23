package com.star.wars.andromeda.tokens

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.star.wars.andromeda.AndromedaAttributeManager
import com.star.wars.andromeda.R

inline val View.fill_active_primary get() = context.fill_active_primary
inline val Fragment.fill_active_primary get() = requireContext().fill_active_primary
inline val Context.fill_active_primary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_active_primary)

inline val View.fill_pressed get() = context.fill_pressed
inline val Fragment.fill_pressed get() = requireContext().fill_pressed
inline val Context.fill_pressed: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_pressed)

inline val View.fill_error_primary get() = context.fill_error_primary
inline val Fragment.fill_error_primary get() = requireContext().fill_error_primary
inline val Context.fill_error_primary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_error_primary)

inline val View.fill_inactive_primary get() = context.fill_inactive_primary
inline val Fragment.fill_inactive_primary get() = requireContext().fill_inactive_primary
inline val Context.fill_inactive_primary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_inactive_primary)

inline val View.fill_background_primary get() = context.fill_background_primary
inline val Fragment.fill_background_primary get() = requireContext().fill_background_primary
inline val Context.fill_background_primary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_background_primary)

inline val View.fill_mute_primary get() = context.fill_mute_primary
inline val Fragment.fill_mute_primary get() = requireContext().fill_mute_primary
inline val Context.fill_mute_primary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_mute_primary)

inline val View.fill_mute_secondary get() = context.fill_mute_secondary
inline val Fragment.fill_mute_secondary get() = requireContext().fill_mute_secondary
inline val Context.fill_mute_secondary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_mute_secondary)

inline val View.fill_background_tertiary get() = context.fill_background_tertiary
inline val Fragment.fill_background_tertiary get() = requireContext().fill_background_tertiary
inline val Context.fill_background_tertiary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_background_tertiary)

inline val View.fill_active_secondary get() = context.fill_active_secondary
inline val Fragment.fill_active_secondary get() = requireContext().fill_active_secondary
inline val Context.fill_active_secondary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_active_secondary)

inline val View.fill_error_secondary get() = context.fill_error_secondary
inline val Fragment.fill_error_secondary get() = requireContext().fill_error_secondary
inline val Context.fill_error_secondary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_error_secondary)

inline val View.fill_inactive_secondary get() = context.fill_inactive_secondary
inline val Fragment.fill_inactive_secondary get() = requireContext().fill_inactive_secondary
inline val Context.fill_inactive_secondary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_inactive_secondary)

inline val View.fill_background_secondary get() = context.fill_background_secondary
inline val Fragment.fill_background_secondary get() = requireContext().fill_background_secondary
inline val Context.fill_background_secondary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.fill_background_secondary)

inline val View.border_active get() = context.border_active
inline val Fragment.border_active get() = requireContext().border_active
inline val Context.border_active: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.border_active)

inline val View.border_pressed get() = context.border_pressed
inline val Fragment.border_pressed get() = requireContext().border_pressed
inline val Context.border_pressed: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.border_pressed)

inline val View.border_inactive get() = context.border_inactive
inline val Fragment.border_inactive get() = requireContext().border_inactive
inline val Context.border_inactive: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.border_inactive)

inline val View.border_static_white get() = context.border_static_white
inline val Fragment.border_static_white get() = requireContext().border_static_white
inline val Context.border_static_white: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.border_static_white)

inline val View.border_mute_primary get() = context.border_mute_primary
inline val Fragment.border_mute_primary get() = requireContext().border_mute_primary
inline val Context.border_mute_primary: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.border_mute_primary)

inline val View.border_focus get() = context.border_focus
inline val Fragment.border_focus get() = requireContext().border_focus
inline val Context.border_focus: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.border_focus)

inline val View.border_error get() = context.border_error
inline val Fragment.border_error get() = requireContext().border_error
inline val Context.border_error: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.border_error)

inline val View.icon_static_black get() = context.icon_static_black
inline val Fragment.icon_static_black get() = requireContext().icon_static_black
inline val Context.icon_static_black: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_static_black)

inline val View.icon_static_white get() = context.icon_static_white
inline val Fragment.icon_static_white get() = requireContext().icon_static_white
inline val Context.icon_static_white: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_static_white)

inline val View.icon_static_grey_inactive get() = context.icon_static_grey_inactive
inline val Fragment.icon_static_grey_inactive get() = requireContext().icon_static_grey_inactive
inline val Context.icon_static_grey_inactive: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_static_grey_inactive)

inline val View.icon_dynamic_default get() = context.icon_dynamic_default
inline val Fragment.icon_dynamic_default get() = requireContext().icon_dynamic_default
inline val Context.icon_dynamic_default: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_dynamic_default)

inline val View.icon_dynamic_inverted get() = context.icon_dynamic_inverted
inline val Fragment.icon_dynamic_inverted get() = requireContext().icon_dynamic_inverted
inline val Context.icon_dynamic_inverted: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_dynamic_inverted)

inline val View.icon_dynamic_red_error get() = context.icon_dynamic_red_error
inline val Fragment.icon_dynamic_red_error get() = requireContext().icon_dynamic_red_error
inline val Context.icon_dynamic_red_error: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_dynamic_red_error)

inline val View.icon_static_payments get() = context.icon_static_payments
inline val Fragment.icon_static_payments get() = requireContext().icon_static_payments
inline val Context.icon_static_payments: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_static_payments)

inline val View.icon_static_inactive get() = context.icon_static_inactive
inline val Fragment.icon_static_inactive get() = requireContext().icon_static_inactive
inline val Context.icon_static_inactive: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_static_inactive)

inline val View.icon_dynamic_active get() = context.icon_dynamic_active
inline val Fragment.icon_dynamic_active get() = requireContext().icon_dynamic_active
inline val Context.icon_dynamic_active: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_dynamic_active)

inline val View.icon_dynamic_inactive get() = context.icon_dynamic_inactive
inline val Fragment.icon_dynamic_inactive get() = requireContext().icon_dynamic_inactive
inline val Context.icon_dynamic_inactive: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_dynamic_inactive)

inline val View.icon_dynamic_error get() = context.icon_dynamic_error
inline val Fragment.icon_dynamic_error get() = requireContext().icon_dynamic_error
inline val Context.icon_dynamic_error: Int
    get() = AndromedaAttributeManager.getColorFromAttribute(this, R.attr.icon_dynamic_error)
