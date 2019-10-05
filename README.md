# Fulcro Expo Template

NOTE: README from the 2.x template, so not sure if it all works.

A bare-bones project using Fulcro 3 with Expo 35.

## Dev setup
- Install yarn
- Install expo cli: `npm install -g expo-cli`
- Do NOT install shadow-cljs globally (will run from the project version)
- Add this line to .bash_profile: `export NODE_OPTIONS=--max_old_space_size=4096`
- You will need Xcode for the iOS emulator

## Android emulator setup
- Install the Android SDK
- In `.bash_profile`, 2 lines will be added by the Android SDK installation. The second one adds various tools to the path, but to make the emulator
work correctly from the command line, you also have to add `${ANDROID_HOME}/emulator` to this list of path updates.
- Unfortunately, you can only get to the AVD manager by running Android Studio, so do that and create a dummy project (just pick whatever and take all the defaults).
- Once the project is created, go to the Tools menu and choose AVD manager
- Create an AVD using the Nexus 6P with Android Pie, and simplify the name to `Nexus6P`
- Go to the command line and run `emulator -list-avds` and you should see `Nexus6P` listed.
- Close Android Studio and hopefully you never have to touch it again. ;)
 
## Client Development

### Starting a build
Start the shadow-cljs server with an IntelliJ runtime config or 

```
$ npm run repl
```

Start a remote REPL connected to localhost on port 9000 and type:

```
(shadow/watch :app)

```

You can also connect to the shadow-cljs server at `http://localhost:9630` and manage your builds there.

### Mobile dev
Run expo with:
 
```
$ expo start
```

The expo tools page will open in your browser.

#### Running on iOS emulator
To run the app on an iOS emulator, just click the option on the tools page.  This will start an iOS emulator, and you can change the emulated device from the menu.  I've been using iPhone X, and once you change it and close, it will use the last device every time you start up, so you will always get the iPhone X

#### Running on Android emulator
To run on the android emulator, you have to start it first.  Fortunately, the configuration earlier lets us do this from the command line, so in a new terminal window type:

```
emulator @Nexus6P
```
 
After the emulator starts, then you can click on the option on the Expo tools page to launch the app on Android emulator, and it will find the running emulator.

#### Connect to mobile REPL
Once the app is running on an emulator, go to your remote REPL (connected to shadow-cljs) and type 

```
(shadow/repl :app)
```

Now you will be in a REPL running on your device and can do REPL-y things with your code, which is one of the top-5 coolest things ever.

### Publish to Expo
```
$ npx shadow-cljs release app
$ expo publish 
```

## Notes

The `:app` build will create an `app/index.js`. In `release` mode that is the only file needed. In dev mode the `app` directory will contain many more `.js` files.

`:init-fn` is called after all files are loaded and in the case of `expo` must render something synchronously as it will otherwise complain about a missing root component. The `shadow.expo/render-root` takes care of registration and setup.

You should disable the `expo` live reload stuff and let `shadow-cljs` handle that instead as they will otherwise interfere with each other.

Source maps don't seem to work properly. `metro` propably doesn't read input source maps when converting sources as things are correctly mapped to the source .js files but not their sources.

Initial load in dev is quite slow since `metro` processes the generated `.js` files.
