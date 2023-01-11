(ns berlin-clock
  (:import (java.time LocalTime)))

(def lights-off (repeat \O))

(defn lights [colors n]
  (let [lights-count (count colors)
        lights-on (take n colors)]
    (->> (concat lights-on lights-off)
         (take lights-count)
         (apply str))))

(defn time->1-minutes [^LocalTime time]
  (lights "YYYY" (mod (.getMinute time) 5)))

(defn time->5-minutes [^LocalTime time]
  (lights "YYRYYRYYRYY" (int (/ (.getMinute time) 5))))

(defn time->1-hours [^LocalTime time]
  (lights "RRRR" (mod (.getHour time) 5)))

  
