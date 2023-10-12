package me.shukawam.fn;

import me.shukawam.fn.data.Response;

public class EntryPoint {
    public Response handleRequest(String name) {
        return new Response("Hi there.");
    }
}
