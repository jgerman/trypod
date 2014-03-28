# -*- mode: ruby -*-
# vi: set ft=ruby :
# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.synced_folder "./", "/home/vagrant/target-app"
  config.vm.synced_folder "~/.m2", "/home/vagrant/.m2"
  config.vm.provision "shell",
    inline: "cd /home/vagrant/target-app;./scripts/migrate.sh;"
  config.vm.box = "trypod-db"                                                   #local name of box
  config.vm.box_url= "https://s3.amazonaws.com/vagrant_trypod/clojure_and_mysql.box" #S3 location of image
  config.vm.network "forwarded_port", guest: 3306, host: 3306
  #end
end

