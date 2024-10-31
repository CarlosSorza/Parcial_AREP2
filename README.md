# Parcial - Carlos Alberto Sorza Gómez

Se crea la clase MathService donde se crean las funciones necesarias para Busquedas, tanto lineal como binaria.
![image](https://github.com/user-attachments/assets/3e53b240-da8d-492f-ae78-446477854f93)

También se creó un servicio Proxy donde en esta clase se genera la conexión con los servidores de MathServices (dos instacias), también este servidor controla las peticiones sobre las instancias
![image](https://github.com/user-attachments/assets/ab79fde0-79bd-4b32-b2fc-7def5f29fac0)

Se crean las dos instancias de MathServices en AWS
![image](https://github.com/user-attachments/assets/bba25761-8b63-4846-a75c-cbbc1effe080)

Se instala java en ambas instancias

![image](https://github.com/user-attachments/assets/9e2b3c54-6eb5-4a39-9ce6-481a9249ea99)

Nos queda restando crear el .jar de cada uno de las instacias de MathService para subirlo a la instancia en AWS
También nos queda faltando crear la instacia necesaria para el Proxy y a su vez subir el correspondiente .jar para las maneje las peticiones para las búsquedas.
