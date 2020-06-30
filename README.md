# git-info-edn

A Leiningen & tools.deps plugin to put git info into a resource.

## Usage

### Leiningen

Put `[git-info-edn "0.2.1"]` into the `:plugins` vector of your `:user` profile.

Or, put `[git-info-edn "0.2.1"]` into the `:plugins` vector of your project.clj.

```shell
    $ lein git-info-edn
```

### tools.deps

Add the following alias to your `deps.edn` file:

```EDN
  :aliases {:git-info-edn {:extra-deps {noisesmith/lein-git-info-edn {:git/url "https://github.com/noisesmith/lein-git-info-edn.git"
                                                                      :sha "GITHUB_SHA_GOES_HERE"}}
                           :main-opts ["-m" "leiningen.git-info-edn"]}}
```

then

```shell
    $ clj -Agit-info-edn
```

Note that you'll have to add the `resources` folder to your `:paths` in `deps.edn`, if it isn't already there (`tools.deps` doesn't include it by default).

```EDN
  :paths ["src" "resources"]
```


## License

Copyright © 2015 Justin Glenn Smith

With contributions by Odin Hole Standal, Peter Monks

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
