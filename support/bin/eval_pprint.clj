#!/usr/bin/env cake run
(require '[clojure.java.io :as io])
(load-file (str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))
(in-ns 'textmate)
(clojure.core/require '[clojure.pprint :as pprint])

(enter-file-ns)

(textmate/attempt
  (clojure.core/println
    (clojure.core/str
        "<pre>"
        (textmate/htmlize
          (pprint/with-pprint-dispatch pprint/code-dispatch
            (pprint/write (clojure.core/eval 
              (clojure.core/read-string (cake/*env* "TM_SELECTED_TEXT")))
              :pretty true :stream nil)))
        "</pre>")))