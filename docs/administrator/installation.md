# Installation instructions
First of all, check the requirements before going further.

## From the archive (stable version)
*Well the  project is in beta so.. there isn't stable version at the moment.*

## From the git repository (development version)
> **⚠ Note** <br>
This method will install the current development version, use at your own risk.

` $ git clone https://github.com/Apereo-Learning-Analytics-Initiative/OpenLRW.git `

## Run the application
> Two bash scripts are located in the `src/scripts/` directory, you will have to move them to `/opt/openlrw`

### Build Script (build.sh)
From the `/opt/openlrw/` directory execute the build script to create the LRW executable.

### Run Script (run.sh)
From the `/opt/openlrw/` directory execute the run script to start the application. Note you will need to update the script below with the appropriate MongoDB path. The application listens on port 9966 (default).
