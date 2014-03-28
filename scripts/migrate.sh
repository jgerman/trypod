#!/bin/sh

export LEIN_ROOT=true
mysqladmin -u root create trypod
/usr/local/bin/lein migratus migrate

