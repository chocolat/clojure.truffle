#!/usr/bin/env cake run
(require '[clojure.java.io :as io])
(load-file (str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))
(in-ns 'textmate)

(when-let [symb	 (get-current-symbol)]
   (when-let [{:keys [file line]} (meta symb)]
     (println
      (format "txmt://open?line=%s&url=file:///%s"
              line 
              (if (.startsWith file "/") 
                file 
                (str (cake/*env* "TM_PROJECT_DIRECTORY") "/src/" file))))))