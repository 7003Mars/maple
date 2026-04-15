package me.mars.maple.schema

import app.cash.redwood.schema.Schema

@Schema(
    members = [
        // Widgets
        BoxP::class,
        RowP::class,
        ColumnP::class,
        ScrollPaneP::class,
        CollapserP::class,
        ButtonP::class,
        LabelP::class,
        ImageP::class,
        ProgressBarP::class,
        BarP::class,
        TextButtonP::class,
        ImageButtonP::class,
        CheckBoxP::class,
        TextFieldP::class,
        TextAreaP::class,
        SliderP::class,
        // Modifiers
        Align::class,
        Padding::class,
        Fill::class,
        SizeIn::class,
        Weight::class,
    ]
)
interface Primitives