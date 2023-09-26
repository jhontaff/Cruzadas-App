# Cruzadas-app
## Readme - Esp
El aplicativo Cruzadas App es una app destinada al proyecto Cruzadas en sitios conectantes el cual busca llevar conectividad
a escuelas en zonas rurales.

### Tecnologías: 
Android Studio, Java, Graddle, Firebase, Firestore, Cloud storage

### Funcionalidad
El objetivo principal de esta app es recolectar datos de campo en tiempo real. Estos datos pueden ser tipo imágen o texto.

Al iniciarse la app el usuario se encontrará con la pantalla de inicio de sesión que le solicitará email y contraseña.
Si el usuario no se encuentra registrado en la plataforma, podrá realizar su registro seleccionando el texto de registro.
El registro le solicitará unos datos personales. Una vez registrado podrá iniciar sesión.

Tan pronto se haya realizado el login, se redirecciona al usuario an módulo de registro de datos, en donde tendrá que ingresar
información sobre la actividad que está realizando en campo. Información como: fecha, nombre, id, hora, imágenes, entre otras...
Después de haber ingresado la información se presiona el botón de guardar datos, el cual, ejecuta la función que almacena la data
en firebase, Firestore y Cloud storage.

En Firebase se visualizará toda la data almacenada de forma organizada por Fecha e Id.

### Pantallas:
Los colores principales manejan rangos de azúl, naranja, blanco.
![log](https://github.com/jhontaff/Cruzadas-app/assets/69325928/0e471075-51bb-410d-873c-53dd0d337e91)
![reg](https://github.com/jhontaff/Cruzadas-app/assets/69325928/c7eaf5d7-3901-448d-9bc3-976a0bfcc5f8)
![olvpw](https://github.com/jhontaff/Cruzadas-app/assets/69325928/c3cac8da-5f61-4a9f-965a-2bb163c8e3c3)
![data](https://github.com/jhontaff/Cruzadas-app/assets/69325928/b74549f1-1935-4b31-bf29-25a347c55c7d)

Los archivos backend de la app los pueden encontrar en la ruta: app/src/main/java/com/cruzadasapp
Login.java , MainActivity.java , Register.java

Los archivos Frontend de la app los pueden encontrar en la ruta: app/src/main/res/layout
activity_login.xml , activity_main.xml , activity_register.xml





## Readme - Eng
Cruzadas App is an app for Cruzada's project, which aims to bring connectivity to schools in rural areas.

### Technologies:
Android Studio, Java, Graddle, Firebase, Firestore, Cloud storage

### What does the app make?
The main objective of this app is to collect field data in real time. This data can be images or text.

When starting the app, the user will be presented with a login screen that will ask for an email and password.
If the user is not already registered on the platform, he/she can register by selecting the registration text.
The registration will ask for some personal data. Once registered, you can log in.

As soon as the login is done, the user will be redirected to the data registration module, where he/she will have to enter
information about the activity he/she is carrying out in the field. Information such as: date, name, id, time, images, among others...
After having entered the information, press the save data button, which executes the function that stores the data in firebase, Firestore
in firebase, Firestore and Cloud storage.

In Firebase you will see all the stored data organised by date and ID.

### Screens:
![log](https://github.com/jhontaff/Cruzadas-app/assets/69325928/0e471075-51bb-410d-873c-53dd0d337e91)
![reg](https://github.com/jhontaff/Cruzadas-app/assets/69325928/c7eaf5d7-3901-448d-9bc3-976a0bfcc5f8)
![olvpw](https://github.com/jhontaff/Cruzadas-app/assets/69325928/c3cac8da-5f61-4a9f-965a-2bb163c8e3c3)
![data](https://github.com/jhontaff/Cruzadas-app/assets/69325928/b74549f1-1935-4b31-bf29-25a347c55c7d)

You can look for the backend Files of this app going to path: app/src/main/java/com/cruzadasapp
Login.java , MainActivity.java , Register.java

You can look for the frontend files of this app going to path: app/src/main/res/layout
activity_login.xml , activity_main.xml , activity_register.xml

