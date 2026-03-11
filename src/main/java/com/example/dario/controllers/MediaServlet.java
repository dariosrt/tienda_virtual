package com.example.dario.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/media"})
public class MediaServlet extends HttpServlet {

    // carpeta fuera del WAR para que sobreviva a redeploys
    private static final String RUTA_UPLOAD = System.getProperty("user.home") + File.separator + "app_uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nombre = req.getParameter("file");
        if (nombre == null || nombre.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        File archivo = new File(RUTA_UPLOAD, nombre);
        if (!archivo.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mime = getServletContext().getMimeType(archivo.getName());
        if (mime == null) mime = "application/octet-stream";

        resp.setContentType(mime);
        resp.setContentLengthLong(archivo.length());
        resp.setHeader("Cache-Control", "public, max-age=86400");

        try (FileInputStream in = new FileInputStream(archivo);
             OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytes;
            while ((bytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
        }
    }
}
// [MermaidChart: 37dfed00-c324-4ec8-9ed9-72e0015cb7b8]
// [MermaidChart: 37dfed00-c324-4ec8-9ed9-72e0015cb7b8]
// [MermaidChart: 37dfed00-c324-4ec8-9ed9-72e0015cb7b8]
// [MermaidChart: 37dfed00-c324-4ec8-9ed9-72e0015cb7b8]
// [MermaidChart: 37dfed00-c324-4ec8-9ed9-72e0015cb7b8]
