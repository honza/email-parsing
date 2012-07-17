Email-message parsing in Clojure
================================

This small library can transform an email message into a Clojure data
structure.  It uses the `javax.mail` library to perform the parsing.

Installation
============

This library hasn't been published to Clojars yet.  Stay tuned.  In the
meantime, you can clone the git repository and play with it.

Usage
=====

This library exposes three functions: `parse-from-string`, `parse-from-file`
and `parse-from-directory`.  While the first two are obvious, the last one is
just a simple convenience function to run `parse-from-file` over each file in a
directory.

``` clojure
(parse-from-filename "/some/path/message")
(parse-from-string (slurp "message"))
```

Warning
=======

This library is in its very early stages.  Also, I'm not exactly an experienced
Clojure developer.  Please be careful.

License
=======

BSD, short and sweet
