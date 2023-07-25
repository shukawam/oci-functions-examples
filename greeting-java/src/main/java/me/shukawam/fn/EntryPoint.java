package me.shukawam.fn;

import me.shukawam.fn.data.Response;

public class EntryPoint {
    public Response handleRequest(String name) {
        var message = (name == null || name.isEmpty()) ? "world" : name;
        return new Response("Hello " + message);
    }
}
