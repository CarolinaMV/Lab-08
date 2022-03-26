package edu.eci.cvds.samples.services;

public class ExcepcionServiciosAlquiler extends Exception{
    public static final String ERROR_REGISTRAR_ITEM = "Se produjo un error al registrar el Item.";
    public static final String ERROR_CONSULTAR_ITEM = "Se produjo un error al buscar un item especifico con su id.";
    public static final String ERROR_CONSULTAR_ITEMS = "Se produjo un error al consultar todos los items.";
    public static final String ERROR_REGISTRAR_CLIENTE = "Se produjo un error al registrar al cliente.";
    public static final String ERROR_CONSULTAR_CLIENTE = "Se produjo un error al buscar un cliente especifico con su id.";
    public static final String ERROR_CONSULTAR_CLIENTES = "Se produjo un error al consultar todos los clientes.";
    public static final String ERROR_REGISTRAR_TIPOITEM = "Se produjo un error al registrar el tipo de item.";
    public static final String ERROR_CONSULTAR_TIPOITEM = "Se produjo un error al buscar un tipo de item especifico con su id.";
    public static final String ERROR_CONSULTAR_TIPOSITEMS = "Se produjo un error al consultar todos los tipos de items.";
    public static final String CLIENTE_NO_REGISTRADO = "El cliente aun no se encuentra registrado.";
    public static final String ITEM_NO_REGISTRADO = "El item aun no se encuentra registrado.";
    public static final String TIPOITEM_NO_REGISTRADO = "El tipo de item aun no se encuentra registrado.";
    public static final String ERROR_DIAS_RENTA = "El número de días registrado para la renta del libro es invalido";
    public static final String ERROR_REGISTRAR_ITEMRENTADO = "Se produjo un error al registrar el nuevo item.";
    public static final String ERROR_VETAR_CLIENTE = "Se produjo un error al vetar al cliente";

    public ExcepcionServiciosAlquiler(String m) {
        super(m);
    }
}
