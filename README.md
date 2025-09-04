techSeller - Advanced Inventory Management System

Project Overview

techSeller is a comprehensive desktop application developed in Java Swing for the management of a technology product inventory. This project serves as a final assignment for an Advanced Programming course, demonstrating a deep understanding of Object-Oriented principles, the MVC (Model-View-Controller) architecture, and robust application design.

The system provides a full suite of features for managing products, which are categorized into Hardware and Software subclasses. All data is persisted to and retrieved from flat text files, simulating a real-world data storage mechanism without a formal database.

Core Concepts & Skills Applied

This project was built with a strong emphasis on clean architecture and modern programming practices.

1. MVC (Model-View-Controller) Design Pattern

The application is strictly organized following the MVC pattern to ensure a clear separation of concerns, making the code scalable, maintainable, and testable.

    Model: Located in the modelo package, this layer represents the application's data and business logic. It includes:

        Inheritance: An abstract Producto class with concrete Hardware and Software subclasses.

        Composition: The Producto class is composed of an InformacionDeStock object, representing a "has-a" relationship.

        Aggregation: The Producto class also holds a collection of Vendedor objects, demonstrating a "one-to-many" relationship.

    View: Located in the vista package, this layer is responsible for the UI. The views are designed to be "dumb," meaning they only display data and capture user input, delegating all logic to the controller. Key Swing components used include JFrame, JPanel, JTable with DefaultTableModel, and dynamic layouts.

    Controller: Located in the controlador package, this layer acts as the intermediary. It handles user events (via ActionListener and ItemListener), processes user input, interacts with the DAO layer to fetch or save data, and updates the View accordingly.

2. Data Persistence (DAO Pattern)

Data is persisted using a DAO (Data Access Object) layer, which abstracts the storage mechanism from the rest of the application.

    Interface-Based Design: The IProductoDAO interface defines a contract for data operations, while ProductoDAO provides the concrete implementation for handling flat files.

    Serialization/Deserialization: Custom logic was implemented to serialize Java objects into semicolon-separated strings for storage in .txt files and deserialize them back into objects for use in the application.

3. Exception Handling

The application is built to be robust by handling potential errors gracefully.

    Standard Exceptions: try-catch blocks are used to manage common errors like IOException, FileNotFoundException, and NumberFormatException during file operations and data parsing.

    Custom Exceptions: A custom exception, EntidadYaExistenteException, was created to handle specific business logic rules, such as preventing the creation of a product with a duplicate SKU. This demonstrates a more advanced and meaningful approach to error handling.

4. Java Collections Framework

The Java Collections Framework is used extensively to manage groups of objects efficiently.

    ArrayList<Producto> is the primary structure used in the DAO and controllers to hold, filter, and manage the list of all products for display in the JTable and for statistical calculations.

    ArrayList<Vendedor> is used within the Producto model to implement the one-to-many aggregation relationship.

🚀 Key Features

    Product Management (CRUD): Add, search, update, and delete products (Hardware & Software).

    Massive Query: View all products in a JTable with dynamic filtering by name and description.

    Statistics Module: Calculate inventory metrics and export reports to a cumulative JSON file using the Streaming API.

    Dynamic UI: Panels adapt to show specific product fields, and JComboBoxes are populated based on user selections in other components.

⚙️ How to Run

    Clone the Repository:
    Bash

git clone <your-repository-url>

Import Project into Eclipse/IDE.

Configure Build Path:

    The project requires the Jakarta JSON Processing library. The javax.json-1.1.4.jar is included in the /lib folder.

    Right-click the project > Build Path > Configure Build Path.

    Go to the Libraries tab, click Add JARs..., and select the .jar file from the /lib folder.

Update module-info.java:

    Ensure your src/module-info.java file contains:
    Java

        requires java.json;

    Run: Execute Main.java to launch the application.

Data File Configuration

For the application to function correctly, ensure the following .txt files are present in the project's root directory:

    marcas.txt: For the brands JComboBox. Format: ID;BRAND_NAME;COUNTRY

    modelos.txt: For the dependent models JComboBox. Fixed-width format.

    lista_estatica.txt: For the static JList. Format: one item per line.
    
✒️ Author

    Nicolás del Rio
