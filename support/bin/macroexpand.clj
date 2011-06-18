#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(load-file (str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(enter-file-ns)

(require '[clojure.pprint :as pprint])

(textmate/attempt
  (clojure.core/println
    (clojure.core/str 
       "<pre>" 
       (textmate/htmlize
         (pprint/with-pprint-dispatch pprint/code-dispatch
           (binding [pprint/*print-suppress-namespaces* true]
             (pprint/write (clojure.core/macroexpand 
               (read-string (cake/*env* "TM_SELECTED_TEXT")))
             :pretty true :stream nil))))
       "</pre>")))