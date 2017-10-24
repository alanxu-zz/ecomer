/**
 * @author alanxu
 *
 */


/*
 * Configure id generation stragegy using Hibernate.
 * Consult org.hibernate.id.enhanced.SequenceStyleGenerator for more parameters.
 * Consult org.hibernate.id.enhanced.StandardOptimizerDescriptor for all optimizer keys.
 */
@org.hibernate.annotations.GenericGenerator(
  name = "ID_GENERATOR",
  strategy = "enhanced-sequence",
  parameters = {
     @org.hibernate.annotations.Parameter(name = "sequence_name", value = "EC_SEQUENCER"),
     @org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
     @org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
     @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo"),
     @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true")
})
package me.alanx.ecomer.bag.test1;