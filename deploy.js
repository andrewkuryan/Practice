#!/usr/bin/env node

const ncp = require('ncp').ncp;
const path = require('path');
const fs = require('fs');
const glob = require('glob');
const {exec} = require('child_process');
const {replace, flatten} = require('lodash');

const tomcatFolder = process.env.CATALINA_HOME;

const appName = "Practice";
const targetFolder = "./build/libs";

(async () => {
    await deploy();
})();

function deploy() {
    if (tomcatFolder !== undefined) {
        return copyFile(
            path.resolve(targetFolder, `${appName}.war`),
            path.resolve(tomcatFolder, `webapps/${appName}.war`)
        );
    } else {
        return Promise.resolve();
    }
}

function copyFile(from, to) {
    return new Promise((res, rej) => {
        fs.copyFile(from, to, (err) => {
            if (err) {
                rej(err);
            } else {
                res();
            }
        });
    })
}
