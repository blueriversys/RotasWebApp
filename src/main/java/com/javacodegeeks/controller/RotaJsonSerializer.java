package com.javacodegeeks.controller;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RotaJsonSerializer implements JsonSerializer<Rota> {
    @Override
    public JsonElement serialize(Rota rota, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("via", context.serialize(rota.getName()));
        object.add("origem", context.serialize(rota.getOrigin()));
        object.add("destino", context.serialize(rota.getDest()));
        object.add("distancia", context.serialize(rota.getDistance()));
        return object;
    }

}
