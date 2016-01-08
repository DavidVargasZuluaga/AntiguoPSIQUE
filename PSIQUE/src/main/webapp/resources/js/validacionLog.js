        $().ready(function() {
        // Validación del formulario, tener en cuenta el ID
        $("#formregistro").validate({ // Aplicación de la funcion validate. A continuación se especifican las reglas y los mensajes

        // Aplicación de REGLAS de validación
        rules: {
        ide: {
        required: true,
        digits: true,
        minlength: 8,
        maxlength: 15
        },
        clave: {
        required: true,
        minlength: 5
        }
        }, // Cierre de las reglas, se separa con coma de los mensajes

        // Mensajes a generar para indicar al usuario la estructura valida que debe ingresar. Se debe indicar por cada regla adicionada anteriormente para sobre escribir los mensajes por defecto (los cuales están en inglés. Se encuentran en "jquery.validate.js" línea 308)
        messages: {
        ide: {
        required: "Campo requerido",
        digits: "Sólo digitos",
        minlength: "Mínimo {0} caracteres",
        maxlength: "Maximo {0} caracteres"
        },
        clave: {
        required: "Campo requerido",
        minlength: "Mínimo {0} caracteres",
        }
        } // Cierre de los mensajes
        }); // Cierre de la función validate
        }); // Cierre de la función ready

        // NOTA: Revise el archivo "additional-methods" (ya enlazado arriba), para obtener otros métodos de validación como "lettersonly" básico para el trabajo de su sistema de información	
