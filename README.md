📱 **ft_hangouts — Android Messenger App**
Ein eigener Messenger für Android (ähnlich wie WhatsApp) für das 42-Projekt ft_hangouts.

📌 **Requirements (Projekt-Anforderungen)**
The App is able to:

👤 Create, Edit, and Delete contacts (with up to 5 details).

💬 Send and receive text messages (SMS) with a clear conversation history.

🏡 Homepage with a chat summary for each contact.

🗃️ Persistent storage using an SQL Database.

🌐 Support 2 different system languages.

⏱️ Show the time the app was in the background when returning.

🎨 Custom menu to change the header color.

📱 Support both Landscape and Portrait modes.

🖼️ App Icon featuring the 42 Logo.

**Bonus Features Included**

📸 Profile picture for each contact.

📞 Direct phone call to contacts.

✨ Clean Material Design interface.


📁 **File Structure & Explanations**
Here is a simple breakdown of the main files and folders in this project:

**System & Configuration Files**

**build.gradle.kts** (Automatically created by Android Studio)

Located at the root of the app folder. It manages dependencies, target Android versions, and build configurations for the project.

**AndroidManifest.xml** (Automatically created by Android Studio)

Located inside src/main/. It defines fundamental app permissions (SMS, Calls), registers app components (Activities, Receivers), and specifies the app icon.

**MainActivity.kt** (Main entry point, modified)

The main activity file that runs when the app starts. It handles runtime permission requests (SMS), manages background-time tracking, and controls overall navigation between screens.

**SmsReceiver.kt**

A BroadcastReceiver that listens for incoming SMS messages in the background, matches the sender number with existing contacts, and automatically saves the message to the database.


🗄️ _**Database (db/ folder)**_
Contains all files required to set up and manage the SQLite Database for contacts and messages:

**Contact.kt** & **Message.kt**: Define the data models for Contacts and Messages.

**DatabaseHelper.kt**: Handles database creation and table setups.

**ContactDao.kt** & **MessageDao.kt**: Contain helper functions to retrieve, insert, edit, and manage contact and message data in SQLite.


🖥️ **Screens (ui/ folder)**
Each screen in the app has its own dedicated Jetpack Compose Kotlin file:

**Start_Screen.kt**: A simple launch screen with navigation buttons to the Main Screen, Contact List, and Settings.

**Main_Screen.kt**: The homepage. Displays all active chats ordered by the newest message. Clicking a chat opens the conversation. Includes a "New Chat" button to select an existing contact or create a new one.

**ContactList_Screen.kt**: Shows a list of all saved contacts with their phone numbers. Offers buttons to create new contacts or open a contact's profile.

**Contact_Screen.kt**: Displays full contact details with options to Edit, Delete, Call, or start a Chat with the contact.

**Chat_Screen.kt**: The messaging screen. Displays chat history sorted chronologically with an input field at the bottom. Uses Android's SmsManager to send real SMS.

**Settings_Screen.kt**: Allows the user to switch the app language and change the header color.

**AppScreens** & **AppScreensRouter.kt**: Define navigation routes between screens and handle parameter passing (e.g., passing a selected Contact to the Chat Screen).


🛠️ **Helper Functions (ui/helpfunctions/)**
Small helper components created to keep the screen files clean and reusable:

**ContactCheck.kt**: Validates user inputs when creating/editing contacts (e.g., checking if phone numbers contain valid digits).

**ContactForm.kt**: Defines the layout and text fields for creating or editing a contact.

**ContactImages.kt**: Handles loading and decoding contact profile images from Bitmaps.

**NavigationsButtons.kt**: Contains universal navigation buttons reused across multiple screens.


🎨 **Theme (ui/theme/)**

**Color.kt, Theme.kt, Type.kt**: Defines the visual styling, colors, and dynamic header themes chosen by the user in Settings.