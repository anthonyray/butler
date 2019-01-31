#!/bin/bash 
BUTLER_VERSION="$(cat ../project.clj | head -n 1 | cut -f 3 -d' ' | tr -d '"')"
echo "Deploying Butler ${BUTLER_VERSION}"
cat stub.sh ../target/uberjar/butler-"$BUTLER_VERSION"-standalone.jar > butler && chmod +x butler
mkdir -p $HOME/Applications/butler
cp butler $HOME/Applications/butler/
rm butler
echo "You might want to add Butler to your Path : export PATH=$HOME/Applications/butler:$PATH"
