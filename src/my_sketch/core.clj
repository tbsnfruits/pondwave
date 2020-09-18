(ns my-sketch.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))


(def ds1 (repeatedly 20 #(rand-int 400)))
(def ds2 (repeatedly 20 #(rand-int 400)))
(def ds (vec (take 20 (repeat 0))))

(def ds-vec (map vector ds1 ds ds2))


(defn setup []
  (q/frame-rate 30)
  {:rad 0
   :dia ds-vec})

(defn update-state [state]
  (let [dia (:dia state)
        rad (:rad state)]
    {:rad (+ 0.03 rad)
     :dia (map (fn [coll]
                 (let [d2 (last coll)
                       d1 (first coll)]
                  (assoc coll 1
                          (+ (* (/ (- d2 d1) 2) (q/cos rad)) (/ (+ d2 d1) 2)))))
               dia)}))


(defn draw-state [state]
  (q/background 50)
  (q/no-fill)
  (q/stroke-weight 1)
  (q/stroke 120)

  (doseq [D (:dia state)
          :let [d (second D)]]
     (q/ellipse (/ (q/width) 2) (/ (q/height) 2) d (/ d 2))))


(q/sketch
 :host "circles"
 :size [500 500]
 :setup setup
 :update update-state
 :draw draw-state
 :middleware [m/fun-mode])
