#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(load-file (str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(enter-file-ns)

(clojure.core/require '[clojure.contrib.repl-utils :as repl-utils])

(textmate/attempt
  (let [sym (symbol (or (cake/*env* "TM_SELECTED_TEXT")
                        (cake/*env* "TM_CURRENT_WORD")))
        src (repl-utils/get-source sym)]
    (if src
      (clojure.core/println (str "<pre>" src "</pre>"))
      (clojure.core/println "Could not find source for" sym))))